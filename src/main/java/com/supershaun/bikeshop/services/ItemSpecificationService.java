package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.CategorySpecification;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemSpecification;
import com.supershaun.bikeshop.models.dto.request.ItemSpecificationAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemSpecificationAdminResponseDto;
import com.supershaun.bikeshop.repositories.CategorySpecificationRepository;
import com.supershaun.bikeshop.repositories.ItemRepository;
import com.supershaun.bikeshop.repositories.ItemSpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemSpecificationService {
    @Autowired
    private ItemSpecificationRepository itemSpecificationRepository;

    @Autowired
    private CategorySpecificationRepository categorySpecificationRepository;

    @Autowired
    private ItemRepository itemRepository;

    public ItemSpecificationAdminResponseDto getAll() {
        return new ItemSpecificationAdminResponseDto(
                itemRepository.findAll(),
                categorySpecificationRepository.findAll(),
                itemSpecificationRepository.findAll()
        );
    }

    @Transactional
    public ItemSpecificationAdminResponseDto.ItemSpecificationAdminDto update(
            Long id,
            ItemSpecificationAdminRequestDto dto
    ) {
        ItemSpecification itemSpecification = itemSpecificationRepository.getById(id);
        Item item = itemRepository.getById(dto.getItemId());
        CategorySpecification categorySpecification =
                categorySpecificationRepository.getById(dto.getCategorySpecificationId());

        itemSpecification.setItem(item);
        itemSpecification.setCategorySpecification(categorySpecification);
        itemSpecification.setValue(dto.getValue());
        itemSpecificationRepository.saveAndFlush(itemSpecification);

        return new ItemSpecificationAdminResponseDto.ItemSpecificationAdminDto(itemSpecification);
    }

    @Transactional
    public ItemSpecificationAdminResponseDto.ItemSpecificationAdminDto create(
            ItemSpecificationAdminRequestDto dto
    ) {
        Item item = itemRepository.getById(dto.getItemId());
        CategorySpecification categorySpecification =
                categorySpecificationRepository.getById(dto.getCategorySpecificationId());

        ItemSpecification itemSpecification = new ItemSpecification(
                item,
                categorySpecification,
                dto.getValue()
        );
        itemSpecificationRepository.saveAndFlush(itemSpecification);

        return new ItemSpecificationAdminResponseDto.ItemSpecificationAdminDto(itemSpecification);
    }

    @Transactional
    public void delete(Long id) {
        itemSpecificationRepository.deleteById(id);
    }
}
