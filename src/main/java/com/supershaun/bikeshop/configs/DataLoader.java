package com.supershaun.bikeshop.configs;

import com.supershaun.bikeshop.models.*;
import com.supershaun.bikeshop.models.enums.ERole;
import com.supershaun.bikeshop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

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

    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @Autowired
    private ItemInstanceSpecificationRepository itemInstanceSpecificationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        CategorySpecification categorySpecification13 = new CategorySpecification(
                category1,
                "Цвет"
        );
        CategorySpecification categorySpecification14 = new CategorySpecification(
                category1,
                "Размер"
        );
        categorySpecificationRepository.saveAll(Arrays.asList(
                categorySpecification11, categorySpecification12, categorySpecification13,
                categorySpecification14, categorySpecification21
        ));

        // Items
        Item item1 = new Item(
                "Trek 4500",
                category21,
                "Только начинаете свой путь в мир маунтинбайка или уже являетесь опытным специалистом в неспешных поездках по городу и за его пределами – с самым универсальным и самым доступным в серии AGGRESSOR SPORT каждый ваш выезд будет сопровождаться комфортом и удовольствием. Как и более дорогие трейл-хартейлы, этот байк оборудован качественными компонентами комплектации и представлен в двух вариантах.",
                50000
        );
        Item item2 = new Item(
                "Trek 3500",
                category21,
                "Только начинаете свой путь в мир маунтинбайка или уже являетесь опытным специалистом в неспешных поездках по городу и за его пределами – с самым универсальным и самым доступным в серии AGGRESSOR SPORT каждый ваш выезд будет сопровождаться комфортом и удовольствием. Как и более дорогие трейл-хартейлы, этот байк оборудован качественными компонентами комплектации и представлен в двух вариантах.",
                40000
        );
        Item item3 = new Item(
                "Trek 2500",
                category21,
                "Только начинаете свой путь в мир маунтинбайка или уже являетесь опытным специалистом в неспешных поездках по городу и за его пределами – с самым универсальным и самым доступным в серии AGGRESSOR SPORT каждый ваш выезд будет сопровождаться комфортом и удовольствием. Как и более дорогие трейл-хартейлы, этот байк оборудован качественными компонентами комплектации и представлен в двух вариантах.",
                30000
        );
        itemRepository.saveAll(Arrays.asList(
                item1, item2, item3
        ));

        // Items Instances
        ItemInstance itemInstance11 = new ItemInstance(item1, 3);
        ItemInstance itemInstance12 = new ItemInstance(item1, 3);
        ItemInstance itemInstance13 = new ItemInstance(item1,  0);
        ItemInstance itemInstance2 = new ItemInstance(item2, 2);
        ItemInstance itemInstance3 = new ItemInstance(item3, 1);
        itemInstanceRepository.saveAll(Arrays.asList(
                itemInstance11, itemInstance12, itemInstance13, itemInstance2, itemInstance3
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

        // Items Instances Specifications
        ItemInstanceSpecification itemInstanceSpecification111 = new ItemInstanceSpecification(
                itemInstance11,
                categorySpecification13,
                "Желтый"
        );
        ItemInstanceSpecification itemInstanceSpecification112 = new ItemInstanceSpecification(
                itemInstance11,
                categorySpecification14,
                "XL"
        );
        ItemInstanceSpecification itemInstanceSpecification121 = new ItemInstanceSpecification(
                itemInstance12,
                categorySpecification13,
                "Красный"
        );
        ItemInstanceSpecification itemInstanceSpecification122 = new ItemInstanceSpecification(
                itemInstance12,
                categorySpecification14,
                "XL"
        );
        ItemInstanceSpecification itemInstanceSpecification131 = new ItemInstanceSpecification(
                itemInstance13,
                categorySpecification13,
                "Синий"
        );
        ItemInstanceSpecification itemInstanceSpecification132 = new ItemInstanceSpecification(
                itemInstance13,
                categorySpecification14,
                "L"
        );
        ItemInstanceSpecification itemInstanceSpecification21 = new ItemInstanceSpecification(
                itemInstance2,
                categorySpecification13,
                "Черный"
        );
        ItemInstanceSpecification itemInstanceSpecification22 = new ItemInstanceSpecification(
                itemInstance2,
                categorySpecification14,
                "XS"
        );
        ItemInstanceSpecification itemInstanceSpecification31 = new ItemInstanceSpecification(
                itemInstance3,
                categorySpecification13,
                "Черный"
        );
        ItemInstanceSpecification itemInstanceSpecification32 = new ItemInstanceSpecification(
                itemInstance3,
                categorySpecification14,
                "M"
        );
        itemInstanceSpecificationRepository.saveAll(Arrays.asList(
                itemInstanceSpecification111, itemInstanceSpecification112, itemInstanceSpecification121,
                itemInstanceSpecification122, itemInstanceSpecification21, itemInstanceSpecification22,
                itemInstanceSpecification31, itemInstanceSpecification32, itemInstanceSpecification131,
                itemInstanceSpecification132
        ));

        String itemImagePath = null;
        try {
            byte[] image = imageRepository.findByName(imagePath);
            itemImagePath = imageRepository.save(image, Paths.get(ItemImage.imagePath, imagePath).toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ItemImage itemImage11 = new ItemImage(itemImagePath, itemInstance11);
        ItemImage itemImage12 = new ItemImage(itemImagePath, itemInstance12);
        ItemImage itemImage2 = new ItemImage(itemImagePath, itemInstance2);
        ItemImage itemImage3 = new ItemImage(itemImagePath, itemInstance3);
        itemImageRepository.saveAll(Arrays.asList(
                itemImage11, itemImage12, itemImage2, itemImage3
        ));

        userLoader();
    }

    private void userLoader() {
        // Roles
        Role roleAdmin = new Role(ERole.ROLE_ADMIN);
        Role roleUser = new Role(ERole.ROLE_USER);
        roleRepository.saveAll(Arrays.asList(
                roleAdmin, roleUser
        ));

        // Users
        User userAdmin = new User(
                "admin@mail.ru",
                "admin",
                passwordEncoder.encode("adminadmin")
        );
        userAdmin.setRoles(Set.of(roleUser, roleAdmin));
        User userSimple = new User(
                "user@mail.ru",
                "user",
                passwordEncoder.encode("useruser")
        );
        userSimple.setRoles(Set.of(roleUser));
        userRepository.saveAll(Arrays.asList(
                userAdmin, userSimple
        ));
    }
}
