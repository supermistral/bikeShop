package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemImage;
import com.supershaun.bikeshop.models.ItemInstance;
import com.supershaun.bikeshop.models.dto.request.ItemInstanceAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemInstanceAdminResponseDto;
import com.supershaun.bikeshop.repositories.ImageRepository;
import com.supershaun.bikeshop.repositories.ItemInstanceRepository;
import com.supershaun.bikeshop.repositories.ItemRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ItemInstanceService {
    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ImageRepository imageRepository;

    public ItemInstanceAdminResponseDto getAll() {
        return new ItemInstanceAdminResponseDto(
                itemRepository.findAll(),
                itemInstanceRepository.findAll()
        );
    }

    @Transactional
    public ItemInstanceAdminResponseDto.ItemInstanceAdminDto create(ItemInstanceAdminRequestDto dto) {
        Item item = itemRepository.getById(dto.getItemId());

        ItemInstance itemInstance = new ItemInstance(
                item,
                dto.getStock()
        );
        itemInstanceRepository.saveAndFlush(itemInstance);

        return new ItemInstanceAdminResponseDto.ItemInstanceAdminDto(itemInstance);
    }

    @Transactional
    public ItemInstanceAdminResponseDto.ItemInstanceAdminDto update(Long id,
                                                                    ItemInstanceAdminRequestDto dto) {
        ItemInstance itemInstance = itemInstanceRepository.getById(id);
        Item item = itemRepository.getById(dto.getItemId());

        itemInstance.setItem(item);
        itemInstance.setStock(dto.getStock());
        itemInstanceRepository.saveAndFlush(itemInstance);

        System.out.println(itemInstance.getItem().getId());

        return new ItemInstanceAdminResponseDto.ItemInstanceAdminDto(itemInstance);
    }

    @Transactional
    public void delete(Long id) {
        ItemInstance itemInstance = itemInstanceRepository.getById(id);
        Set<ItemImage> itemImages = itemInstance.getImages();

        itemImages.forEach(image -> {
            try {
                imageRepository.deleteByName(image.getImage());
            } catch (Exception ex) {}
        });

        itemInstanceRepository.deleteById(id);
    }
}
