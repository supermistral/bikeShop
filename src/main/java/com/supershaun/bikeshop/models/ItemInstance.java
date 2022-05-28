package com.supershaun.bikeshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item_instances")
@Getter
@Setter
@NoArgsConstructor
public class ItemInstance {
    @Id
    @SequenceGenerator(name = "item_instances_seq", sequenceName = "item_instances_sequence", allocationSize = 1)
    @GeneratedValue(generator = "item_instances_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @JsonIgnore
    private Item item;

    @OneToMany(mappedBy = "itemInstance", cascade = CascadeType.ALL)
    @OrderBy("id")
    private Set<ItemInstanceSpecification> specifications;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "itemInstance", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({ "id", "itemInstance" })
    @OrderBy("id")
    private Set<ItemImage> images = new HashSet<>();

    public ItemInstance(Item item, int stock) {
        this.item = item;
        this.stock = stock;
    }
}
