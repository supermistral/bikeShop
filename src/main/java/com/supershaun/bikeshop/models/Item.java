package com.supershaun.bikeshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @Min(value = 1)
    private double price;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private Set<ItemImage> images = new HashSet<>();
}
