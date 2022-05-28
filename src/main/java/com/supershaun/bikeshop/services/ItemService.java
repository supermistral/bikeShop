package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.exceptions.ItemNotFoundException;
import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemInstance;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.models.dto.ItemInstanceDto;
import com.supershaun.bikeshop.models.dto.request.ItemAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemAdminResponseDto;
import com.supershaun.bikeshop.repositories.CategoryRepository;
import com.supershaun.bikeshop.repositories.ItemInstanceRepository;
import com.supershaun.bikeshop.repositories.ItemRepository;
import com.supershaun.bikeshop.services.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService implements IItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ItemAdminResponseDto getAll() {
        return new ItemAdminResponseDto(
                categoryRepository.findAll(),
                itemRepository.findAll()
        );
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

    @Override
    public List<ItemInstanceDto> getAllInstancesByIds(List<Long> ids) {
        List<ItemInstance> itemInstance = itemInstanceRepository.findAllById(ids);

        return itemInstance.stream()
                .sorted(Comparator.comparing(item -> ids.indexOf(item.getId())))
                .map(ItemInstanceDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemAdminResponseDto.ItemAdminDto update(Long id, ItemAdminRequestDto dto) {
        Item item = itemRepository.getById(id);
        Category category = categoryRepository.getById(dto.getCategoryId());

        if (category.getChildren().size() != 0) {
            throw new IllegalStateException("Category of item shouldn't have child categories");
        }

        item.setCategory(category);
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setDescription(dto.getDescription());
        itemRepository.saveAndFlush(item);

        return new ItemAdminResponseDto.ItemAdminDto(item);
    }

    @Override
    @Transactional
    public ItemAdminResponseDto.ItemAdminDto create(ItemAdminRequestDto dto) {
        Category category = categoryRepository.getById(dto.getCategoryId());

        if (category.getChildren().size() != 0) {
            throw new IllegalStateException("Category of item shouldn't have child categories");
        }

        Item item = new Item(
                dto.getName(),
                category,
                dto.getDescription(),
                dto.getPrice()
        );
        itemRepository.saveAndFlush(item);

        return new ItemAdminResponseDto.ItemAdminDto(item);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        itemInstanceRepository.deleteById(id);
    }
}
