package com.supershaun.bikeshop.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInstanceSpecificationAdminRequestDto {
    private Long itemInstanceId;
    private Long categorySpecificationId;
    private String value;
}
