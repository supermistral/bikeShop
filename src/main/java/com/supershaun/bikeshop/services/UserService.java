package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Role;
import com.supershaun.bikeshop.models.User;
import com.supershaun.bikeshop.models.dto.request.UserAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.UserAdminResponseDto;
import com.supershaun.bikeshop.models.enums.ERole;
import com.supershaun.bikeshop.repositories.RoleRepository;
import com.supershaun.bikeshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserAdminResponseDto getAll() {
        List<User> users = userRepository.findAll();
        List<Role> roles = roleRepository.findAll();

        return new UserAdminResponseDto(users, roles);
    }

    private List<Role> getRolesByHigherRoleName(String name) {
        List<ERole> eRoles = new ArrayList<>();
        ERole eRole;

        try {
            eRole = ERole.valueOf(name);
        } catch (Exception ex) {
            eRole = null;
        }

        if (eRole != null) {
            switch (eRole) {
                case ROLE_ADMIN:
                    eRoles.addAll(Arrays.asList(
                            ERole.ROLE_ADMIN, ERole.ROLE_MANAGER, ERole.ROLE_USER
                    ));
                    break;
                case ROLE_MANAGER:
                    eRoles.addAll(Arrays.asList(
                            ERole.ROLE_MANAGER, ERole.ROLE_USER
                    ));
                    break;
                default:
                    eRoles.add(ERole.ROLE_USER);
            }
        } else {
            eRoles.add(ERole.ROLE_USER);
        }

        return eRoles.stream()
                .map(r -> roleRepository.getByName(r))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserAdminResponseDto.UserAdminDto update(Long id, UserAdminRequestDto dto) {
        User user = userRepository.getById(id);
        List<Role> roles = getRolesByHigherRoleName(dto.getRole());

        user.setRoles((Set<Role>) roles);
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.saveAndFlush(user);

        return new UserAdminResponseDto.UserAdminDto(user);
    }

    @Transactional
    public UserAdminResponseDto.UserAdminDto create(UserAdminRequestDto dto) {
        List<Role> roles = getRolesByHigherRoleName(dto.getRole());

        User user = new User(
                dto.getEmail(),
                dto.getName(),
                passwordEncoder.encode(dto.getPassword())
        );
        user.setRoles((Set<Role>) roles);
        userRepository.saveAndFlush(user);

        return new UserAdminResponseDto.UserAdminDto(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
