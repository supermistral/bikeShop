package com.supershaun.bikeshop.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private String email;
    private List<String> roles;

    public UserResponseDto(String accessToken, String refreshToken, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
        this.roles = roles;
    }
}
