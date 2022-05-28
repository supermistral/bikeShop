package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.CategorySpecification;
import com.supershaun.bikeshop.models.ItemInstance;
import com.supershaun.bikeshop.models.ItemInstanceSpecification;
import com.supershaun.bikeshop.models.dto.request.ItemInstanceSpecificationAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemInstanceSpecificationAdminResponseDto;
import com.supershaun.bikeshop.repositories.CategorySpecificationRepository;
import com.supershaun.bikeshop.repositories.ItemInstanceRepository;
import com.supershaun.bikeshop.repositories.ItemInstanceSpecificationRepository;
import com.supershaun.bikeshop.utils.SpecificationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemInstanceSpecificationService {
    @Autowired
    private ItemInstanceSpecificationRepository itemInstanceSpecificationRepository;

    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    @Autowired
    private CategorySpecificationRepository categorySpecificationRepository;

    public ItemInstanceSpecificationAdminResponseDto getAll() {
        List<ItemInstance> itemInstances = itemInstanceRepository.findAll();
        List<CategorySpecification> categorySpecifications = categorySpecificationRepository.findAll();
        List<ItemInstanceSpecification> itemInstanceSpecifications = itemInstanceSpecificationRepository.findAll();

        return new ItemInstanceSpecificationAdminResponseDto(
                itemInstances,
                categorySpecifications,
                itemInstanceSpecifications
        );
    }

    @Transactional
    public ItemInstanceSpecificationAdminResponseDto.ItemInstanceSpecificationAdminDto update(
            Long id,
            ItemInstanceSpecificationAdminRequestDto dto
    ) {
        ItemInstanceSpecification itemInstanceSpecification = itemInstanceSpecificationRepository.getById(id);
        CategorySpecification categorySpecification = categorySpecificationRepository.getById(
                dto.getCategorySpecificationId()
        );
        ItemInstance itemInstance = itemInstanceRepository.getById(dto.getItemInstanceId());

        if (!checkValueInCategorySpecificationChoice(categorySpecification, dto.getValue())) {
            throw new IllegalStateException("Value of the specification must be set according to category choices");
        }

        itemInstanceSpecification.setCategorySpecification(categorySpecification);
        itemInstanceSpecification.setItemInstance(itemInstance);
        itemInstanceSpecification.setValue(dto.getValue());
        itemInstanceSpecificationRepository.saveAndFlush(itemInstanceSpecification);

        return new ItemInstanceSpecificationAdminResponseDto.ItemInstanceSpecificationAdminDto(
                itemInstanceSpecification
        );
    }

    @Transactional
    public ItemInstanceSpecificationAdminResponseDto.ItemInstanceSpecificationAdminDto create(
            ItemInstanceSpecificationAdminRequestDto dto
    ) {
        CategorySpecification categorySpecification = categorySpecificationRepository.getById(
                dto.getCategorySpecificationId()
        );
        ItemInstance itemInstance = itemInstanceRepository.getById(dto.getItemInstanceId());

        if (!checkValueInCategorySpecificationChoice(categorySpecification, dto.getValue())) {
            throw new IllegalStateException("Value of the specification must be set according to category choices");
        }

        ItemInstanceSpecification itemInstanceSpecification = new ItemInstanceSpecification(
                itemInstance,
                categorySpecification,
                dto.getValue()
        );
        itemInstanceSpecificationRepository.saveAndFlush(itemInstanceSpecification);

        return new ItemInstanceSpecificationAdminResponseDto.ItemInstanceSpecificationAdminDto(
                itemInstanceSpecification
        );
    }

    private boolean checkValueInCategorySpecificationChoice(CategorySpecification categorySpecification, String value) {
        String choices = categorySpecification.getChoices();

        if (choices == null || choices.isEmpty())
            return true;

        List<String> parsedChoices = SpecificationParser.parseKeys(choices);
        return parsedChoices.indexOf(value) != -1;
    }

    @Transactional
    public void delete(Long id) {
        itemInstanceSpecificationRepository.deleteById(id);
    }
}
