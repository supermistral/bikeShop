package com.supershaun.bikeshop.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequestDto {
    @NotBlank
    private String refreshToken;
}
