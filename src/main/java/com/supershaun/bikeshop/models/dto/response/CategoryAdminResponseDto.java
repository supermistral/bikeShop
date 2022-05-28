package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryAdminResponseDto {
    private Long id;
    private CategoryParentDto parent;
    private String name;
    private String image;

    public CategoryAdminResponseDto(Category category) {
        id = category.getId();
        parent = category.getParent() != null
                ? new CategoryParentDto(
                    category.getParent().getId(),
                    category.getParent().getName()
                ) : null;
        name = category.getName();
        image = category.getImage();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CategoryParentDto {
        private Long id;
        private String name;
    }
}
