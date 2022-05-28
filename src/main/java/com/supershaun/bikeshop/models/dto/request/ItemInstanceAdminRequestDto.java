package com.supershaun.bikeshop.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInstanceAdminRequestDto {
    private Long itemId;
    private int stock;
}
