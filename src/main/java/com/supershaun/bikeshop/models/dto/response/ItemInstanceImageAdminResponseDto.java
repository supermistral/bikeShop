package com.supershaun.bikeshop.models.dto.response;

import com.supershaun.bikeshop.models.ItemImage;
import com.supershaun.bikeshop.models.ItemInstance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemInstanceImageAdminResponseDto {
    private List<ItemInstanceAdminDto> itemInstances;
    private List<ItemInstanceImageAdminDto> images;

    public ItemInstanceImageAdminResponseDto(List<ItemInstance> itemInstances,
                                             List<ItemImage> images) {
        this.itemInstances = itemInstances.stream()
                .map(ItemInstanceAdminDto::new)
                .collect(Collectors.toList());
        this.images = images.stream()
                .map(ItemInstanceImageAdminDto::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class ItemInstanceImageAdminDto {
        private Long id;
        private String image;
        private ItemInstanceAdminDto itemInstance;

        public ItemInstanceImageAdminDto(ItemImage itemImage) {
            id = itemImage.getId();
            image = itemImage.getImage();
            itemInstance = new ItemInstanceAdminDto(itemImage.getItemInstance());
        }
    }

    @Getter
    @Setter
    public static class ItemInstanceAdminDto {
        private Long id;
        private String name;

        public ItemInstanceAdminDto(ItemInstance itemInstance) {
            id = itemInstance.getId();
            name = itemInstance.getItem().getName();
        }
    }
}
