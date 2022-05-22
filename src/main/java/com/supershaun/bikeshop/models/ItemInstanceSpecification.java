package com.supershaun.bikeshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_instance_specifications")
@Getter
@Setter
@NoArgsConstructor
public class ItemInstanceSpecification {
    @Id
    @SequenceGenerator(name = "item_instance_specifications_seq", sequenceName = "item_instance_specifications_sequence", allocationSize = 1)
    @GeneratedValue(generator = "item_instance_specifications_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @NotNull
    @JsonIgnore
    private ItemInstance itemInstance;

    @ManyToOne
    @JoinColumn(name = "category_specification_id", referencedColumnName = "id")
    @NotNull
    @JsonIgnore
    private CategorySpecification categorySpecification;

    @Column(name = "value")
    private String value;

    public ItemInstanceSpecification(ItemInstance itemInstance, CategorySpecification categorySpecification, String value) {
        this.itemInstance = itemInstance;
        this.categorySpecification = categorySpecification;
        this.value = value;
    }
}
