package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemImage;
import com.supershaun.bikeshop.models.ItemInstance;
import com.supershaun.bikeshop.models.ItemInstanceSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemInstanceDto {
    private Long id;
    private int stock;
    private String image;
    private List<ItemInstanceSpecificationDto> specifications;
    private Item item;

    @Getter
    @Setter
    public static class ItemInstanceSpecificationDto {
        private String key, value;

        public ItemInstanceSpecificationDto(ItemInstanceSpecification specification) {
            key = specification.getCategorySpecification().getName();
            value = specification.getValue();
        }
    }

    public ItemInstanceDto(ItemInstance itemInstance) {
        id = itemInstance.getId();
        stock = itemInstance.getStock();
        specifications = itemInstance.getSpecifications().stream()
                .map(ItemInstanceSpecificationDto::new)
                .collect(Collectors.toList());
        item = itemInstance.getItem();

        ItemImage itemImage = itemInstance.getImages().stream()
                .findFirst()
                .orElse(null);

        if (itemImage == null)
            image = null;
        else
            image = itemImage.getImage();
    }
}
