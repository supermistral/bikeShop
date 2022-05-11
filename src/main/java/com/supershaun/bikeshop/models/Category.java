package com.supershaun.bikeshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @SequenceGenerator(name = "categories_seq", sequenceName = "categories_sequence", allocationSize = 1)
    @GeneratedValue(generator = "categories_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    @JsonIgnoreProperties("parent")
    private Set<Category> children = new HashSet<>();

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Item> items;

    @Column(name = "image")
    private String image;

    static public String imagePath = "categories/";

    public Category(String name, Category parent, String description, String image) {
        this.name = name;
        this.parent = parent;
        this.description = description;
        this.image = image;
    }
}
