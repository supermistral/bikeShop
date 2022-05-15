package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.utils.SpecificationParser;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryDetailDto {
    private String name;
    private List<CategoryChildDto> lastChildren;
    private Map<String, List<String>> specifications;
    private List<Item> items;

    @Getter
    @Setter
    public static class CategoryChildDto {
        private Long id;
        private String name;

        public CategoryChildDto(Category category) {
            id = category.getId();
            name = category.getName();
        }
    }

    public CategoryDetailDto(Category category) {
        name = category.getName();
        lastChildren = createLastChildren(category);
        items = createItemsOfLastChildren(category);
        specifications = createSpecificationsMap(category, items);
    }

    private Map<String, List<String>> createSpecificationsMap(Category category, List<Item> items) {
        // Set (instead of List) to avoid duplicates
        Map<String, List<String>> specifications = new HashMap<>();

        Set<String> specificationKeys = new HashSet<>();
        fillSpecificationsKeys(category, specificationKeys);

        items.forEach(item -> {
            String itemSpecifications = item.getSpecifications();
            specificationKeys.forEach(key -> {
                String value = SpecificationParser.parseValueByKey(itemSpecifications, key);
                if (value != null)
                    addToMapList(specifications, key, value);
            });
        });

        return specifications;
    }

    private synchronized void addToMapList(Map<String, List<String>> map, String key, String value) {
        List<String> list = map.get(key);
        if (list == null) {
            list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
        } else if (!list.contains(value)) {
            list.add(value);
        }
    }

    private void fillSpecificationsKeys(Category currentCategory, Set<String> keys) {
        if (currentCategory == null)
            return;

        keys.addAll(SpecificationParser.parseKeys(currentCategory.getSpecifications()));
        fillSpecificationsKeys(currentCategory.getParent(), keys);
    }

    private List<CategoryChildDto> createLastChildren(Category category) {
        List<CategoryChildDto> lastChildren = new LinkedList<>();

        // Start creating from children of the current category
        category.getChildren().forEach(child -> fillLastChildrenList(child, lastChildren));

        return lastChildren.stream()
                .sorted(Comparator.comparing(CategoryChildDto::getName))
                .collect(Collectors.toList());
    }

    private void fillLastChildrenList(Category currentCategory, List<CategoryChildDto> lastChildren) {
        if (currentCategory.getChildren().size() == 0)
            lastChildren.add(new CategoryChildDto(currentCategory));

        currentCategory.getChildren().forEach(child -> fillLastChildrenList(child, lastChildren));
    }

    private List<Item> createItemsOfLastChildren(Category category) {
        List<Item> items = new LinkedList<>();

        fillItemsOfLastChildren(category, items);

        return items.stream()
                .sorted(Comparator.comparing(Item::getPrice))
                .collect(Collectors.toList());
    }

    private void fillItemsOfLastChildren(Category currentCategory, List<Item> items) {
        if (currentCategory.getChildren().size() == 0)
            items.addAll(currentCategory.getItems());

        currentCategory.getChildren().forEach(child -> fillItemsOfLastChildren(child, items));
    }
}
