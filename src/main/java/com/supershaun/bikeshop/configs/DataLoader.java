package com.supershaun.bikeshop.configs;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.ItemImage;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.repositories.CategoryRepository;
import com.supershaun.bikeshop.repositories.ImageRepository;
import com.supershaun.bikeshop.repositories.ItemImageRepository;
import com.supershaun.bikeshop.repositories.ItemRepository;
import com.supershaun.bikeshop.services.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Override
    public void run(ApplicationArguments args) {
        String imagePath = "category.png";
        String newImagePath = null;

        try {
            byte[] image = imageRepository.findByName(imagePath);
            newImagePath = imageRepository.save(image, Paths.get(Category.imagePath, imagePath).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Category category1 = new Category(
                "Велосипеды",
                null,
                "Пол;Возраст",
                newImagePath
        );
        Category category2 = new Category(
                "Горные велосипеды",
                category1,
                "",
                newImagePath
        );
        Category category3 = new Category(
                "Городские велосипеды",
                category1,
                "",
                newImagePath
        );
        Category category4 = new Category(
                "Экстремальные велосипеды",
                category1,
                "",
                newImagePath
        );
        Category category21 = new Category(
                "Хардтейлы",
                category2,
                "",
                newImagePath
        );
        Category category22 = new Category(
                "Двухподвесы",
                category2,
                "",
                newImagePath
        );
        categoryRepository.saveAll(Arrays.asList(
                category1, category2, category3, category4, category21, category22)
        );

        Item item1 = new Item(
                "Trek 4500",
                category21,
                "пол:унисекс;возраст:для взрослых",
                50000,
                3
        );
        Item item2 = new Item(
                "Trek 3500",
                category21,
                "Пол:женский;Возраст:для взрослых",
                40000,
                2
        );
        Item item3 = new Item(
                "Trek 2500",
                category21,
                "Пол:унисекс;Возраст:для детей",
                30000,
                1
        );
        itemRepository.saveAll(Arrays.asList(
                item1, item2, item3
        ));

        String itemImagePath = null;
        try {
            byte[] image = imageRepository.findByName(imagePath);
            itemImagePath = imageRepository.save(image, Paths.get(ItemImage.imagePath, imagePath).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ItemImage itemImage1 = new ItemImage(itemImagePath, item1);
        ItemImage itemImage2 = new ItemImage(itemImagePath, item2);
        ItemImage itemImage3 = new ItemImage(itemImagePath, item3);
        itemImageRepository.saveAll(Arrays.asList(
                itemImage1, itemImage2, itemImage3
        ));
    }
}
