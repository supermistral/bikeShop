package com.supershaun.bikeshop.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSpecificationAdminRequestDto {
    private String value;
    private Long itemId;
    private Long categorySpecificationId;
}
