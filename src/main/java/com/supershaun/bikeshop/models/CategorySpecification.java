package com.supershaun.bikeshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "category_specifications")
@Getter
@Setter
@NoArgsConstructor
public class CategorySpecification {
    @Id
    @SequenceGenerator(name = "category_specifications_seq", sequenceName = "category_specifications_sequence", allocationSize = 1)
    @GeneratedValue(generator = "category_specifications_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "name")
    private String name;

    @Column(name = "choices")
    private String choices;

    @OneToMany(mappedBy = "categorySpecification")
    @JsonIgnore
    private Set<ItemSpecification> itemSpecifications;

    public CategorySpecification(Category category, String name, String choices) {
        this.category = category;
        this.name = name;
        this.choices = choices;
    }
}
