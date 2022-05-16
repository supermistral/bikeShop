package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.CategorySpecification;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemSpecification;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryDetailDto {
    private String name;
    private List<CategoryChildDto> lastChildren;
    private List<CategorySpecificationDto> specifications;
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

    @Getter
    @Setter
    public static class CategorySpecificationDto {
        private Long id;
        private String key;
        private List<String> values;

        public CategorySpecificationDto(CategorySpecification categorySpecification, List<String> values) {
            id = categorySpecification.getId();
            key = categorySpecification.getName();
            this.values = values;
        }
    }

    public CategoryDetailDto(Category category) {
        name = category.getName();
        lastChildren = createLastChildren(category);
        items = createItemsOfLastChildren(category);
        specifications = createSpecifications(category, items);
    }

    private List<CategorySpecificationDto> createSpecifications(Category category, List<Item> items) {
        List<CategorySpecificationDto> specifications = new LinkedList<>();

        // Sorted map for sorting specifications: specifications of the higher categories in the hierarchy
        //      are higher; within same category sorting by name
        Map<CategorySpecification, Set<String>> categorySpecificationsMap = new TreeMap<>((o1, o2) -> {
            if (o1.getCategory().getId() == o2.getCategory().getId())
                return o1.getName().compareTo(o2.getName());

            return o2.getCategory().getChildren().size() - o1.getCategory().getChildren().size();
        });

        // Set (instead of List) to avoid duplicates
        Set<CategorySpecification> categorySpecifications = new HashSet<>();
        fillSpecificationsKeys(category, categorySpecifications);

        // Create map for each category specification
        categorySpecifications.forEach(s -> categorySpecificationsMap.put(s, new HashSet<>()));

        // For each item need to get it's specifications and put it into list of map
        //      only if parent (category's) specification contains in built 'category specifications' list
        items.forEach(item -> {
            Set<ItemSpecification> itemSpecifications = item.getSpecifications();

            itemSpecifications.forEach(itemSpecification -> {
                CategorySpecification categorySpecification = itemSpecification.getCategorySpecification();
                Set<String> itemCategorySpecifications = categorySpecificationsMap.get(categorySpecification);

                if (itemCategorySpecifications != null)
                    itemCategorySpecifications.add(itemSpecification.getValue());
            });
        });

        // Transform map to list
        categorySpecificationsMap.forEach((k, v) -> specifications.add(new CategorySpecificationDto(
                        k,
                        v.stream().sorted().collect(Collectors.toList())
                ))
        );

        return specifications;
    }

    private void fillSpecificationsKeys(Category category, Collection<CategorySpecification> specifications) {
        if (category == null)
            return;

        specifications.addAll(
                category.getSpecifications().stream()
                        .filter(s -> !s.getChoices().isEmpty())     // Without choices - is not filterable
                        .collect(Collectors.toList())
        );
        fillSpecificationsKeys(category.getParent(), specifications);
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
