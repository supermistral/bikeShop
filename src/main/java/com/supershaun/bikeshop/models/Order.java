package com.supershaun.bikeshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supershaun.bikeshop.models.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(generator = "orders_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "order")
    @OrderBy("id")
    @Size(min = 1)
    private Set<QuantityItem> quantityItems = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "created_at")
    public Date createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonIgnore
    private User user;

    public Order(User user) {
        this.status = OrderStatus.Created;
        this.user = user;
        this.createdAt = new Date();
    }

    public double getPrice() {
        double price = 0;
        for (QuantityItem item : quantityItems) {
            price += item.getQuantity() * item.getItemInstance().getItem().getPrice();
        }
        return price;
    }
}
