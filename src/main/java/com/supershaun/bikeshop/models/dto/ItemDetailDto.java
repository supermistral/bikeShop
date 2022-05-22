package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemDetailDto {
    private Long id;
    private String name;
    private List<ItemInstanceDto> instances;
    private List<ItemSpecificationDto> specifications;
    private String description;
    private double price;
    private Company company;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ItemSpecificationDto {
        private String key, value;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ItemInstanceSpecificationDto {
        private Long id;
        private String key, value;
    }

    @Getter
    @Setter
    public static class ItemInstanceDto {
        private Long id;
        private int stock;
        private List<String> images;
        private List<ItemInstanceSpecificationDto> specifications;

        public ItemInstanceDto(ItemInstance itemInstance) {
            id = itemInstance.getId();
            stock = itemInstance.getStock();
            images = itemInstance.getImages().stream()
                    .map(image -> image.getImage())
                    .collect(Collectors.toList());
            specifications = itemInstance.getSpecifications().stream()
                    .map(s -> new ItemInstanceSpecificationDto(
                            s.getCategorySpecification().getId(),
                            s.getCategorySpecification().getName(),
                            s.getValue()
                    ))
                    .collect(Collectors.toList());
        }
    }

    public ItemDetailDto(Item item) {
        id = item.getId();
        name = item.getName();
        instances = item.getInstances().stream()
                .map(i -> new ItemInstanceDto(i))
                .collect(Collectors.toList());
        specifications = item.getSpecifications().stream()
                .map(i -> new ItemSpecificationDto(
                        i.getCategorySpecification().getName(),
                        i.getValue()
                ))
                .sorted(Comparator.comparing(ItemSpecificationDto::getKey))
                .collect(Collectors.toList());
        description = item.getDescription();
        price = item.getPrice();
        company = item.getCompany();
    }
}
