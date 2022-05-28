package com.supershaun.bikeshop.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemAdminRequestDto {
    private String name;
    private double price;
    private String description;
    private Long categoryId;
}
