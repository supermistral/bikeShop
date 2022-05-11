package com.supershaun.bikeshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_images")
@Getter
@Setter
@NoArgsConstructor
public class ItemImage {
    @Id
    @SequenceGenerator(name = "item_images_seq", sequenceName = "item_images_sequence", allocationSize = 1)
    @GeneratedValue(generator = "item_images_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

}
