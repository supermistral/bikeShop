package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.CategoryWithoutChildrenDto;
import com.supershaun.bikeshop.repositories.CategoryRepository;
import com.supershaun.bikeshop.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllWithDepth() {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParent() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryWithoutChildrenDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryWithoutChildrenDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getItemsById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return ItemService.filterAndSort(new ArrayList<>(category.get().getItems()));
        }
        return null;
    }
}
