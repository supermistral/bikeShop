package com.supershaun.bikeshop.configs;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.repositories.CategoryRepository;
import com.supershaun.bikeshop.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) {
        Category category1 = new Category(
                "Велосипеды",
                null,
                ""
        );
        Category category2 = new Category(
                "Горные велосипеды",
                category1,
                ""
        );
        Category category3 = new Category(
                "Городские велосипеды",
                category1,
                ""
        );
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));

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
