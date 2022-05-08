package com.supershaun.bikeshop.models;

import com.supershaun.bikeshop.models.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "order")
    private Set<QuantityItem> quantityItems = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
