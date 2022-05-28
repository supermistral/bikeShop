package com.supershaun.bikeshop.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminRequestDto {
    private String email;
    private String name;
    private String role;
    private String password;
}
