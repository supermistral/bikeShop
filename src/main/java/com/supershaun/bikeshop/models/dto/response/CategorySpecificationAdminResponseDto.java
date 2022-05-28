package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.CategorySpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategorySpecificationAdminResponseDto {
    private List<CategoryDto> categories;
    private List<CategorySpecificationAdminDto> categorySpecifications;

    public CategorySpecificationAdminResponseDto(List<Category> categories,
                                                 List<CategorySpecification> categorySpecifications) {
        this.categories = categories.stream()
                .map(item -> new CategoryDto(item.getId(), item.getName()))
                .collect(Collectors.toList());
        this.categorySpecifications = categorySpecifications.stream()
                .map(CategorySpecificationAdminDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class CategorySpecificationAdminDto {
        private Long id;
        private CategoryDto category;
        private String name;
        private String choices;

        public CategorySpecificationAdminDto(CategorySpecification categorySpecification) {
            id = categorySpecification.getId();
            category = new CategoryDto(
                    categorySpecification.getCategory().getId(),
                    categorySpecification.getCategory().getName()
            );
            name = categorySpecification.getName();
            choices = categorySpecification.getChoices();
        }
    }



    @Getter
    @Setter
    @AllArgsConstructor
    public static class CategoryDto {
        private Long id;
        private String name;
    }
}
