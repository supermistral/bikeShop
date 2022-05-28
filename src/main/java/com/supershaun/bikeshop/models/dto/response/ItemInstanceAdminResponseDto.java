package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemInstance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemInstanceAdminResponseDto {
    private List<ItemDto> items;
    private List<ItemInstanceAdminDto> itemInstances;

    public ItemInstanceAdminResponseDto(List<Item> items, List<ItemInstance> itemInstances) {
        this.items = items.stream()
                .map(item -> new ItemDto(item.getId(), item.getName()))
                .collect(Collectors.toList());
        this.itemInstances = itemInstances.stream()
                .map(ItemInstanceAdminDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class ItemInstanceAdminDto {
        private Long id;
        private String image;
        private ItemDto item;
        private int stock;

        public ItemInstanceAdminDto(ItemInstance itemInstance) {
            id = itemInstance.getId();
            item = new ItemDto(itemInstance.getItem().getId(), itemInstance.getItem().getName());
            stock = itemInstance.getStock();

            if (itemInstance.getImages().size() != 0)
                image = itemInstance.getImages().stream().findFirst().get().getImage();
            else
                image = null;
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    public static class ItemDto {
        private Long id;
        private String name;
    }
}
