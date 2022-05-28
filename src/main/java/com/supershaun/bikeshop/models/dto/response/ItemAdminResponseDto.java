package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemAdminResponseDto {
    private List<CategorySpecificationAdminResponseDto.CategoryDto> categories;
    private List<ItemAdminDto> items;

    public ItemAdminResponseDto(List<Category> categories, List<Item> items) {
        this.categories = categories.stream()
                .filter(item -> item.getChildren().size() == 0)
                .map(item -> new CategorySpecificationAdminResponseDto.CategoryDto(
                        item.getId(), item.getName()
                ))
                .collect(Collectors.toList());
        this.items = items.stream()
                .map(ItemAdminDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class ItemAdminDto {
        private Long id;
        private String name;
        private double price;
        private String description;
        private CategorySpecificationAdminResponseDto.CategoryDto category;

        public ItemAdminDto(Item item) {
            id = item.getId();
            name = item.getName();
            price = item.getPrice();
            description = item.getDescription();
            category = new CategorySpecificationAdminResponseDto.CategoryDto(
                    item.getCategory().getId(),
                    item.getCategory().getName()
            );
        }
    }
}
