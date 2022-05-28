package com.supershaun.bikeshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "quantity_items")
@Getter
@Setter
@NoArgsConstructor
public class QuantityItem {
    @Id
    @SequenceGenerator(name = "quantity_items_seq", sequenceName = "quantity_items_sequence", allocationSize = 1)
    @GeneratedValue(generator = "quantity_items_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemInstance itemInstance;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity")
    @Min(value = 1)
    private int quantity;

    public QuantityItem(ItemInstance itemInstance, Order order, int quantity) {
        this.itemInstance = itemInstance;
        this.order = order;
        this.quantity = quantity;
    }
}
