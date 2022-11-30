package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private String status;
    private Date createdAt;
    private List<QuantityItemDto> quantityItems;
    private double price;

    public OrderDto() {}

    public OrderDto(Order order) {
        id = order.getId();
        createdAt = order.getCreatedAt();
        status = order.getStatus().name();
        quantityItems = order.getQuantityItems().stream()
                .map(QuantityItemDto::new)
                .collect(Collectors.toList());
        price = order.getPrice();
    }

    @Getter
    @Setter
    public static class QuantityItemDto {
        private ItemInstanceDto itemInstance;
        private int quantity;

        public QuantityItemDto(QuantityItem quantityItem) {
            quantity = quantityItem.getQuantity();
            itemInstance = new ItemInstanceDto(quantityItem.getItemInstance());
        }
    }

    @Getter
    @Setter
    public static class ItemInstanceDto {
        private Long id;
        private Long itemId;
        private Long categoryId;
        private String name;
        private double price;
        private String image;
        private List<ItemInstanceSpecificationDto> specifications;

        public ItemInstanceDto(ItemInstance itemInstance) {
            id = itemInstance.getId();
            categoryId = itemInstance.getItem().getCategory().getId();
            itemId = itemInstance.getItem().getId();
            name = itemInstance.getItem().getName();
            price = itemInstance.getItem().getPrice();
            specifications = itemInstance.getSpecifications().stream()
                    .map(ItemInstanceSpecificationDto::new)
                    .collect(Collectors.toList());

            ItemImage itemImage = itemInstance.getImages().stream()
                    .findFirst()
                    .orElse(null);

            if (itemImage == null)
                image = null;
            else
                image = itemImage.getImage();
        }
    }

    @Getter
    @Setter
    public static class ItemInstanceSpecificationDto {
        private String key;
        private String value;

        public ItemInstanceSpecificationDto(ItemInstanceSpecification specification) {
            key = specification.getCategorySpecification().getName();
            value = specification.getValue();
        }
    }
}
