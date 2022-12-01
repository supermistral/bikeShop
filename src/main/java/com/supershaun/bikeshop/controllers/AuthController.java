package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.exceptions.EmailAlreadyInUseException;
import com.supershaun.bikeshop.exceptions.RoleNotFoundException;
import com.supershaun.bikeshop.exceptions.TokenRefreshException;
import com.supershaun.bikeshop.models.RefreshToken;
import com.supershaun.bikeshop.models.Role;
import com.supershaun.bikeshop.models.User;
import com.supershaun.bikeshop.models.dto.*;
import com.supershaun.bikeshop.models.enums.ERole;
import com.supershaun.bikeshop.repositories.RoleRepository;
import com.supershaun.bikeshop.repositories.UserRepository;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.security.JwtUserDetails;
import com.supershaun.bikeshop.security.jwt.JwtUtils;
import com.supershaun.bikeshop.services.RefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Tag(name="Аутентификация", description = "Методы для работы с аутентификацией и авторизацией")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    @Operation(summary = "Аутентифицироваться",
            description = "Получить токены доступа и обновления и пользовательские данные")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Аутентификация выполенена успешно",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUserDetails userDetails = (JwtUserDetails)authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new UserResponseDto(
                jwt,
                refreshToken.getToken(),
                userDetails.getEmail(),
                roles
        ));
    }

    @PostMapping("/signup")
    @Operation(summary = "Зарегистрироваться",
            description = "Зарегистрироваться по пользовательским данным")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация выполнена успешно",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "400", description = "Аккаунт с таким email уже существует",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        User user = new User(
                signUpRequest.getEmail(),
                signUpRequest.getName(),
                encoder.encode(signUpRequest.getPassword())
        );

        Set<Role> roles = getRoles(Arrays.asList("user"));

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new DefaultMessageEntity(Messages.UserRegistredSuccessfully.toString()));
    }

    private Set<Role> getRoles(List<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException());
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException());
                        roles.add(adminRole);
                        break;

                    case "manager":
                        Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RoleNotFoundException());
                        roles.add(managerRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException());
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }

    @PostMapping("/token")
    @Operation(summary = "Обновить токен доступа",
            description = "Получить токен доступа по токену обновления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(schema = @Schema(implementation = RefreshTokenRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Токен обновления не найден",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "401", description = "Токен обновления просрочен",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> getToken(@Valid @RequestBody RefreshTokenRequestDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(
                            user.getEmail(),
                            user.getRoles().stream()
                                    .map(role -> role.getName().name())
                                    .collect(Collectors.toList())
                    );
                    return ResponseEntity.ok(new RefreshTokenResponseDto(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @DeleteMapping("/token")
    @Operation(summary = "Выйти из аккаунта",
            description = "Удалить токен обновления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выход выполнен успешно",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "401", description = "Некорректный токен",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new TokenRefreshException(authorizationHeader, "Token is not valid");
        }

        String email;
        try {
            email = jwtUtils.getUserNameFromJwtToken(authorizationHeader.substring(7));
        } catch (ExpiredJwtException ex) {
            email = ex.getClaims().getSubject();
        }

        refreshTokenService.deleteByUserEmail(email);
        return ResponseEntity.ok(new DefaultMessageEntity(Messages.LogoutSuccessfully.toString()));
    }

}
