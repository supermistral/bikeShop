package com.supershaun.bikeshop.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInstanceImageAdminRequestDto {
    private Long itemInstanceId;
    private String image;
}
