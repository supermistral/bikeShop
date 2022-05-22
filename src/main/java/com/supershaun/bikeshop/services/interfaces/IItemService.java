package com.supershaun.bikeshop.services.interfaces;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;

import java.util.List;
import java.util.Map;

public interface IItemService {
    List<Item> getAll();
    List<Item> getByIds(List<Long> ids);
    ItemDetailDto getById(Long id);
}
