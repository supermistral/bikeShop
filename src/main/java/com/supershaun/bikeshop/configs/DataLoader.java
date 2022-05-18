package com.supershaun.bikeshop.configs;

import com.supershaun.bikeshop.models.*;
import com.supershaun.bikeshop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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

    @Autowired
    private CategorySpecificationRepository categorySpecificationRepository;

    @Autowired
    private ItemSpecificationRepository itemSpecificationRepository;

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

        // Categories
        Category category1 = new Category(
                "Велосипеды",
                null,
                newImagePath
        );
        Category category2 = new Category(
                "Горные велосипеды",
                category1,
                newImagePath
        );
        Category category3 = new Category(
                "Городские велосипеды",
                category1,
                newImagePath
        );
        Category category4 = new Category(
                "Экстремальные велосипеды",
                category1,
                newImagePath
        );
        Category category21 = new Category(
                "Хардтейлы",
                category2,
                newImagePath
        );
        Category category22 = new Category(
                "Двухподвесы",
                category2,
                newImagePath
        );
        categoryRepository.saveAll(Arrays.asList(
                category1, category2, category3, category4, category21, category22)
        );

        // Categories specifications
        CategorySpecification categorySpecification11 = new CategorySpecification(
                category1,
                "Сезон",
                "2017;2018;2019"
        );
        CategorySpecification categorySpecification12 = new CategorySpecification(
                category1,
                "Пол",
                "Унисекс;Для женщин"
        );
        CategorySpecification categorySpecification21 = new CategorySpecification(
                category2,
                "Назначение",
                "Кросс-кантри;Эндуро;Даунхилл;Триал"
        );
        categorySpecificationRepository.saveAll(Arrays.asList(
                categorySpecification11, categorySpecification12, categorySpecification21
        ));

        // Items
        Item item1 = new Item(
                "Trek 4500",
                category21,
                50000,
                3
        );
        Item item2 = new Item(
                "Trek 3500",
                category21,
                40000,
                2
        );
        Item item3 = new Item(
                "Trek 2500",
                category21,
                30000,
                1
        );
        itemRepository.saveAll(Arrays.asList(
                item1, item2, item3
        ));

        // Items Specifications
        ItemSpecification itemSpecification11 = new ItemSpecification(
                item1,
                categorySpecification11,
                "2017"
        );
        ItemSpecification itemSpecification12 = new ItemSpecification(
                item1,
                categorySpecification12,
                "Унисекс"
        );
        ItemSpecification itemSpecification13 = new ItemSpecification(
                item1,
                categorySpecification21,
                "Кросс-кантри"
        );
        ItemSpecification itemSpecification21 = new ItemSpecification(
                item2,
                categorySpecification11,
                "2018"
        );
        ItemSpecification itemSpecification22 = new ItemSpecification(
                item2,
                categorySpecification12,
                "Для женщин"
        );
        ItemSpecification itemSpecification23 = new ItemSpecification(
                item2,
                categorySpecification21,
                "Кросс-кантри"
        );
        ItemSpecification itemSpecification31 = new ItemSpecification(
                item3,
                categorySpecification11,
                "2018"
        );
        ItemSpecification itemSpecification32 = new ItemSpecification(
                item3,
                categorySpecification12,
                "Унисекс"
        );
        ItemSpecification itemSpecification33 = new ItemSpecification(
                item3,
                categorySpecification21,
                "Кросс-кантри"
        );
        itemSpecificationRepository.saveAll(Arrays.asList(
                itemSpecification11, itemSpecification12, itemSpecification13,
                itemSpecification21, itemSpecification22, itemSpecification23,
                itemSpecification31, itemSpecification32, itemSpecification33
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
