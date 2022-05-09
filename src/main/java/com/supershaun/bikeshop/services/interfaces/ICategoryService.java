package com.supershaun.bikeshop.services.interfaces;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.CategoryWithoutChildrenDto;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllWithDepth();
    List<CategoryWithoutChildrenDto> getAll();
    List<Item> getItemsById(Long id);
}
