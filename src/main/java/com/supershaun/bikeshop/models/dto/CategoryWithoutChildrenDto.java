package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CategoryWithoutChildrenDto {
    private Long id;
    private String name;
    private Category parent;
    private String description;

    public CategoryWithoutChildrenDto(Category category) {
        id = category.getId();
        name = category.getName();
        parent = category.getParent();
        description = category.getDescription();
    }
}
