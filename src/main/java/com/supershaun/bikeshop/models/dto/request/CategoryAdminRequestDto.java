package com.supershaun.bikeshop.models.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAdminRequestDto {
    private Long parentId;
    private String name;
    private String image;
}
