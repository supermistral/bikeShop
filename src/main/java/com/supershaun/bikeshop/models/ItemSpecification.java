package com.supershaun.bikeshop.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_specifications")
@Getter
@Setter
@NoArgsConstructor
public class ItemSpecification {
    @Id
    @SequenceGenerator(name = "item_specifications_seq", sequenceName = "item_specifications_sequence", allocationSize = 1)
    @GeneratedValue(generator = "item_specifications_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @NotNull
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_specification_id", referencedColumnName = "id")
    @NotNull
    private CategorySpecification categorySpecification;

    @Column(name = "value")
    private String value;

    public ItemSpecification(Item item, CategorySpecification categorySpecification, String value) {
        this.item = item;
        this.categorySpecification = categorySpecification;
        this.value = value;
    }
}
