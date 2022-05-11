package com.supershaun.bikeshop.configs;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.ItemImage;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.repositories.CategoryRepository;
import com.supershaun.bikeshop.repositories.ImageRepository;
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

    @Override
    public void run(ApplicationArguments args) {
        String imagePath = "category.png";
        String newImagePath = null;

        try {
            byte[] image = imageRepository.findByName(imagePath);
            newImagePath = imageRepository.save(image, Paths.get(Category.imagePath, imagePath.toString()).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Category category1 = new Category(
                "Велосипеды",
                null,
                "",
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

        Item item = new Item(
                "Trek 4500",
                category2,
                "Description",
                50000,
                2
        );
        itemRepository.save(item);
    }
}
