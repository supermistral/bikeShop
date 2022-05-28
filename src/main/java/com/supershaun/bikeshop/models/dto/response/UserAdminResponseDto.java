package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.Role;
import com.supershaun.bikeshop.models.User;
import com.supershaun.bikeshop.models.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserAdminResponseDto {
    private List<RoleDto> roles;
    private List<UserAdminDto> users;

    public UserAdminResponseDto(List<User> users, List<Role> roles) {
        this.roles = roles.stream()
                .map(role -> new RoleDto(role.getName().name()))
                .collect(Collectors.toList());
        this.users = users.stream()
                .map(UserAdminDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class UserAdminDto {
        private Long id;
        private String email;
        private String name;
        private String role;
        private String password;

        public UserAdminDto(User user) {
            id = user.getId();
            email = user.getEmail();
            name = user.getName();
            password = user.getPassword();

            List<ERole> eRolesFromLowerToHigher = Arrays.asList(
                    ERole.ROLE_USER, ERole.ROLE_MANAGER, ERole.ROLE_ADMIN
            );
            int roleHigherIndex = 0;
            int roleIndex;
            for (Role role : user.getRoles()) {
                roleIndex = eRolesFromLowerToHigher.indexOf(role.getName());
                if (roleIndex > roleHigherIndex)
                    roleHigherIndex = roleIndex;
            }

            this.role = eRolesFromLowerToHigher.get(roleHigherIndex).name();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RoleDto {
        private String name;
    }
}
