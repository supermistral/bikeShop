package com.supershaun.bikeshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @SequenceGenerator(name = "items_seq", sequenceName = "items_sequence", allocationSize = 1)
    @GeneratedValue(generator = "items_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    @OrderBy("id")
    private Set<ItemInstance> instances = new HashSet<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ItemSpecification> specifications;

    @Column(name = "description", columnDefinition="TEXT")
    @JsonIgnore
    private String description;

    @Column(name = "price")
    @Min(value = 1)
    private double price;

    public Item(String name, Category category, String description, double price) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public int getStock() {
        return instances.stream().mapToInt(ItemInstance::getStock).sum();
    }

    public String getImage() {
        if (instances.size() == 0)
            return null;

        ItemInstance instance = instances.stream().findAny().orElse(null);
        if (instance == null)
            return null;

        return instance.getImages().stream()
                .map(ItemImage::getImage)
                .findAny()
                .orElse(null);
    }
}
