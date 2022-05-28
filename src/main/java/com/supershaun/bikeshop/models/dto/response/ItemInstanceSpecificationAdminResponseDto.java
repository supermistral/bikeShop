package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.CategorySpecification;
import com.supershaun.bikeshop.models.ItemInstance;
import com.supershaun.bikeshop.models.ItemInstanceSpecification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemInstanceSpecificationAdminResponseDto {
    private List<ItemInstanceAdminResponseDto.ItemInstanceAdminDto> itemInstances;
    private List<CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto> categorySpecifications;
    private List<ItemInstanceSpecificationAdminDto> itemInstanceSpecifications;

    public ItemInstanceSpecificationAdminResponseDto(List<ItemInstance> itemInstances,
                                                     List<CategorySpecification> categorySpecifications,
                                                     List<ItemInstanceSpecification> itemInstanceSpecifications) {
        this.itemInstances = itemInstances.stream()
                .map(ItemInstanceAdminResponseDto.ItemInstanceAdminDto::new)
                .collect(Collectors.toList());
        this.categorySpecifications = categorySpecifications.stream()
                .map(CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto::new)
                .collect(Collectors.toList());
        this.itemInstanceSpecifications = itemInstanceSpecifications.stream()
                .map(ItemInstanceSpecificationAdminDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class ItemInstanceSpecificationAdminDto {
        private Long id;
        private ItemInstanceDto itemInstance;
        private ItemSpecificationAdminResponseDto.CategorySpecificationDto categorySpecification;
        private String value;
        private boolean isChoice;

        public ItemInstanceSpecificationAdminDto(ItemInstanceSpecification itemInstanceSpecification) {
            id = itemInstanceSpecification.getId();
            value = itemInstanceSpecification.getValue();
            itemInstance = new ItemInstanceDto(
                    itemInstanceSpecification.getItemInstance().getId(),
                    itemInstanceSpecification.getItemInstance().getItem().getName()
            );
            categorySpecification = new ItemSpecificationAdminResponseDto.CategorySpecificationDto(
                    itemInstanceSpecification.getCategorySpecification().getId(),
                    itemInstanceSpecification.getCategorySpecification().getName(),
                    itemInstanceSpecification.getCategorySpecification().getChoices()
            );
            isChoice = itemInstanceSpecification.getCategorySpecification().getChoices() != null;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ItemInstanceDto {
        private Long id;
        private String name;
    }
}
