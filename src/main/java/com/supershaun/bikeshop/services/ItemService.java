package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.repositories.ItemRepository;
import com.supershaun.bikeshop.services.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAll() {
//        return filterAndSort(itemRepository.findAll());
        return null;
    }

    @Override
    public List<Item> getByIds(List<Long> ids) {
        List<Item> items = itemRepository.findAllById(ids);

        Collections.sort(items, Comparator.comparing(item -> ids.indexOf(item.getId())));

        return items;
    }

    @Override
    public ItemDetailDto getById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent())
            return null;

        return new ItemDetailDto(item.get());
    }
}
