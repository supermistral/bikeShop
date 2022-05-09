package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.repositories.ItemRepository;
import com.supershaun.bikeshop.services.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAll() {
        return filterAndSort(itemRepository.findAll());
    }

    public static List<Item> filterAndSort(List<Item> items) {
        return items.stream()
                .filter(item -> item.getStock() > 0)
                .sorted((item1, item2) -> item1.getName().compareTo(item2.getName()))
                .collect(Collectors.toList());
    }
}
