package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.models.dto.request.UserAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.UserAdminResponseDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Пользователи", description = "Методы для работы с пользовательскими аккаунтами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
})
@SecurityRequirement(name = "JWT Authorization")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserAdminResponseDto.class))))
    })
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("{id}")
    @Operation(summary = "Обновить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь обновлен",
                    content = @Content(schema = @Schema(implementation = UserAdminResponseDto.UserAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody UserAdminRequestDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь создан",
                    content = @Content(schema = @Schema(implementation = UserAdminResponseDto.UserAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> create(@RequestBody UserAdminRequestDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
