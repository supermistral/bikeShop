package com.supershaun.bikeshop.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    @Email(message = "Неверный формат email")
    private String email;

    @NotBlank
    private String password;
}
