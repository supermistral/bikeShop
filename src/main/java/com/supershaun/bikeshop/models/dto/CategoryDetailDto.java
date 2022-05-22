package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.*;
import com.supershaun.bikeshop.utils.SpecificationParser;
import lombok.AllArgsConstructor;
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
    private Price price;

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

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Price {
        private double min, max;
    }

    public CategoryDetailDto(Category category) {
        name = category.getName();
        lastChildren = createLastChildren(category);
        specifications = createSpecifications(category);
        price = createPrice(category);
    }

    private Price createPrice(Category category) {
        List<Item> items = new LinkedList<>();
        fillItemsOfLastChildren(category, items);

        if (items.size() == 0)
            return new Price(0, 0);

        List<Double> itemsPrices = items.stream()
                .map(Item::getPrice)
                .collect(Collectors.toList());

        double minPrice = Collections.min(itemsPrices);
        double maxPrice = Collections.max(itemsPrices);

        return new Price(minPrice, maxPrice);
    }

    private List<CategorySpecificationDto> createSpecifications(Category category) {
        List<CategorySpecificationDto> specifications = new LinkedList<>();

        // Set (instead of List) to avoid duplicates
        // Sorted set for sorting specifications: specifications of the higher categories in the hierarchy
        //      are higher; within same category sorting by name
        Set<CategorySpecification> categorySpecifications = new TreeSet<>((o1, o2) -> {
            if (o1.getCategory().getId() == o2.getCategory().getId())
                return o1.getName().compareTo(o2.getName());
            return o2.getCategory().getChildren().size() - o1.getCategory().getChildren().size();
        });

        // Recursive filling
        fillSpecificationsKeys(category, categorySpecifications);

        categorySpecifications.forEach(spec -> specifications.add(new CategorySpecificationDto(
                spec,
                SpecificationParser.parseKeys(spec.getChoices()).stream()
                        .sorted()
                        .collect(Collectors.toList())
        )));

        return specifications;
    }

    private void fillSpecificationsKeys(Category category, Collection<CategorySpecification> specifications) {
        if (category == null)
            return;

        specifications.addAll(
                category.getSpecifications().stream()
                        .filter(s -> s.getChoices() != null)    // Without choices - is not filterable
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
