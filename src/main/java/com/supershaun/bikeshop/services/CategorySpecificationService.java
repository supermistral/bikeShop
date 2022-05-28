package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.CategorySpecification;
import com.supershaun.bikeshop.models.dto.request.CategorySpecificationAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.CategorySpecificationAdminResponseDto;
import com.supershaun.bikeshop.repositories.CategoryRepository;
import com.supershaun.bikeshop.repositories.CategorySpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorySpecificationService {
    @Autowired
    private CategorySpecificationRepository categorySpecificationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public CategorySpecificationAdminResponseDto getAll() {
        return new CategorySpecificationAdminResponseDto(
                categoryRepository.findAll(),
                categorySpecificationRepository.findAll()
        );
    }

    @Transactional
    public CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto update(
            Long id,
            CategorySpecificationAdminRequestDto dto
    ) {
        CategorySpecification categorySpecification = categorySpecificationRepository.getById(id);
        Category category = categoryRepository.getById(dto.getCategoryId());

        categorySpecification.setCategory(category);
        categorySpecification.setName(dto.getName());
        categorySpecification.setChoices(dto.getChoices());

        return new CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto(categorySpecification);
    }

    public CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto create(
            CategorySpecificationAdminRequestDto dto
    ) {
        Category category = categoryRepository.getById(dto.getCategoryId());
        CategorySpecification categorySpecification = new CategorySpecification(
                category,
                dto.getName(),
                dto.getChoices()
        );
        categorySpecificationRepository.saveAndFlush(categorySpecification);

        return new CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto(categorySpecification);
    }

    public void delete(Long id) {
        categorySpecificationRepository.deleteById(id);
    }
}
