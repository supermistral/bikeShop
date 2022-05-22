package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.CategorySpecification;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemSpecification;
import com.supershaun.bikeshop.models.dto.CategoryDetailDto;
import com.supershaun.bikeshop.models.dto.CategoryWithoutChildrenDto;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.repositories.CategoryRepository;
import com.supershaun.bikeshop.repositories.CategorySpecificationRepository;
import com.supershaun.bikeshop.services.interfaces.ICategoryService;
import com.supershaun.bikeshop.utils.SpecificationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategorySpecificationRepository categorySpecificationRepository;

    @Override
    public List<Category> getAllWithDepth() {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParent() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryWithoutChildrenDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryWithoutChildrenDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDetailDto getById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return new CategoryDetailDto(category.get());
        }
        return null;
    }

    private List<Item> getItemsOfLastChildrenById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent())
            return null;

        List<Item> items = new LinkedList<>();
        fillItemsOfLastChildren(items, category.get());

        return filterAndSort(items);
    }

    private void fillItemsOfLastChildren(List<Item> items, Category category) {
        if (category == null)
            return;
        else if (category.getChildren().size() == 0)
            items.addAll(category.getItems());

        category.getChildren().forEach(child -> fillItemsOfLastChildren(items, child));
    }

    @Override
    public List<Item> getItemsById(Long id, Map<String, String> params) {
        List<Item> items = getItemsOfLastChildrenById(id);

        if (items == null)
            return null;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();
            List<String> values = SpecificationParser.parseKeys(value);

            if (values.size() == 0)
                continue;

            try {
                Long categorySpecificationId = Long.parseLong(key);
                items = filterByCategorySpecificationId(items, categorySpecificationId, values);
            } catch (NumberFormatException ex) {
                switch (key) {
                    case "price":
                        String[] pricesString = values.get(0).split("-");
                        if (pricesString.length != 2)
                            break;

                        double minPrice, maxPrice;
                        try {
                            minPrice = Double.parseDouble(pricesString[0]);
                            maxPrice = Double.parseDouble(pricesString[1]);
                        } catch (NumberFormatException e) {
                            break;
                        }

                        items = filterByPrice(items, minPrice, maxPrice);
                        break;

                    case "category":
                        List<Long> categoryIds;
                        try {
                            categoryIds = values.stream()
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                        } catch (NumberFormatException e) {
                            break;
                        }

                        items = filterByCategoryIds(items, categoryIds);
                        break;
                }
            }
        }

        return filterAndSort(items);
    }

    private List<Item> filterByCategorySpecificationId(
            List<Item> items,
            Long categorySpecificationId,
            List<String> values
    ) {
        Optional<CategorySpecification> categorySpecification = categorySpecificationRepository.findById(categorySpecificationId);

        if (!categorySpecification.isPresent())
            return items;

        List<String> choices = SpecificationParser.parseKeys(categorySpecification.get().getChoices()).stream()
                .sorted()
                .collect(Collectors.toList());

        return items.stream()
                .filter(item -> {
                    List<ItemSpecification> itemSpecifications = item.getSpecifications().stream()
                            .filter(s -> s.getCategorySpecification().getId() == categorySpecificationId)
                            .collect(Collectors.toList());

                    if (itemSpecifications.size() != 1)
                        return true;

                    ItemSpecification itemSpecification = itemSpecifications.get(0);
                    int valueIndexInChoices = choices.indexOf(itemSpecification.getValue());

                    return values.stream().anyMatch(value -> value.equals(String.valueOf(valueIndexInChoices)));
                })
                .collect(Collectors.toList());
    }

    private List<Item> filterByCategoryIds(List<Item> items, List<Long> categoryIds) {
        return items.stream()
                .filter(item -> categoryIds.contains(item.getCategory().getId()))
                .collect(Collectors.toList());
    }

    private List<Item> filterByPrice(List<Item> items, double minPrice, double maxPrice) {
        return items.stream()
                .filter(item -> item.getPrice() >= minPrice && item.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public static List<Item> filterAndSort(List<Item> items) {
        return items.stream()
                .sorted((item1, item2) -> (int) (item1.getPrice() - item2.getPrice()))
                .collect(Collectors.toList());
    }
}
