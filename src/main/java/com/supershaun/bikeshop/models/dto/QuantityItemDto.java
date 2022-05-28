package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.ItemInstance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantityItemDto {
    private ItemInstance itemInstance;
    private int quantity;
}
