package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.CategorySpecification;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemSpecificationAdminResponseDto {
    private List<ItemInstanceAdminResponseDto.ItemDto> items;
    private List<CategorySpecificationDto> categorySpecifications;
    private List<ItemSpecificationAdminDto> itemSpecifications;

    public ItemSpecificationAdminResponseDto(List<Item> items,
                                             List<CategorySpecification> categorySpecifications,
                                             List<ItemSpecification> itemSpecifications) {
        this.items = items.stream()
                .map(item -> new ItemInstanceAdminResponseDto.ItemDto(item.getId(), item.getName()))
                .collect(Collectors.toList());
        this.categorySpecifications = categorySpecifications.stream()
                .map(item -> new CategorySpecificationDto(item.getId(), item.getName(), item.getChoices()))
                .collect(Collectors.toList());
        this.itemSpecifications = itemSpecifications.stream()
                .map(ItemSpecificationAdminDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class ItemSpecificationAdminDto {
        private Long id;
        private String value;
        private ItemInstanceAdminResponseDto.ItemDto item;
        private CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto categorySpecification;

        public ItemSpecificationAdminDto(ItemSpecification itemSpecification) {
            id = itemSpecification.getId();
            value = itemSpecification.getValue();
            item = new ItemInstanceAdminResponseDto.ItemDto(
                    itemSpecification.getItem().getId(),
                    itemSpecification.getItem().getName()
            );
            categorySpecification = new CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto(
                    itemSpecification.getCategorySpecification()
            );
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CategorySpecificationDto {
        private Long id;
        private String name;
        private String choices;
    }
}
