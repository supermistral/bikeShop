package com.supershaun.bikeshop.models.dto.request;

import com.supershaun.bikeshop.models.dto.response.CategorySpecificationAdminResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySpecificationAdminRequestDto {
    private Long categoryId;
    private String name;
    private String choices;
}
