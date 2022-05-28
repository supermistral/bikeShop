package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.ItemImage;
import com.supershaun.bikeshop.models.ItemInstance;
import com.supershaun.bikeshop.models.dto.response.ItemInstanceImageAdminResponseDto;
import com.supershaun.bikeshop.repositories.ImageRepository;
import com.supershaun.bikeshop.repositories.ItemImageRepository;
import com.supershaun.bikeshop.repositories.ItemInstanceRepository;
import com.supershaun.bikeshop.services.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService implements IImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @Override
    public String save(byte[] image, String name) throws Exception {
        return imageRepository.save(image, name);
    }

    @Override
    public byte[] findByName(String name) throws Exception {
        return imageRepository.findByName(name);
    }

    @Transactional
    public String saveToItemInstance(byte[] image, String name, Long id) throws Exception {
        ItemInstance itemInstance = itemInstanceRepository.getById(id);

        String savedName = save(image, Paths.get(ItemImage.imagePath, name).toString());
        ItemImage itemImage = new ItemImage(savedName, itemInstance);
        itemImageRepository.save(itemImage);

        return savedName;
    }

    public ItemInstanceImageAdminResponseDto getAllImageInstances() {
        List<ItemInstance> itemInstances = itemInstanceRepository.findAll();
        List<ItemImage> itemImages = itemImageRepository.findAll();

        return new ItemInstanceImageAdminResponseDto(
                itemInstances,
                itemImages
        );
    }

    @Transactional
    public String updateImageInstance(Long id, Long itemInstanceId, byte[] image, String name) throws Exception {
        ItemImage itemImage = itemImageRepository.getById(id);
        ItemInstance itemInstance = itemInstanceRepository.getById(itemInstanceId);
        String newName = null;

        if (image != null) {
            try {
                imageRepository.deleteByName(itemImage.getImage());
                newName = save(image, Paths.get(ItemImage.imagePath, name).toString());
            } catch (Exception ex) {}

            if (newName != null)
                itemImage.setImage(newName);
        }

        itemImage.setItemInstance(itemInstance);
        itemImageRepository.save(itemImage);

        return newName;
    }

    @Transactional
    public void deleteImageInstance(Long id) {
        ItemImage itemImage = itemImageRepository.getById(id);

        try {
            imageRepository.deleteByName(itemImage.getImage());
        } catch (Exception ex) {}

        itemImageRepository.deleteById(id);
    }
}
