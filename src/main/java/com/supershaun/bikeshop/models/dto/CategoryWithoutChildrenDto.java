package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryWithoutChildrenDto {
    private Long id;
    private String name;
    private Category parent;

    public CategoryWithoutChildrenDto(Category category) {
        id = category.getId();
        name = category.getName();
        parent = category.getParent();
    }
}
