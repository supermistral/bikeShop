package com.supershaun.bikeshop.services.interfaces;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.CategoryDetailDto;
import com.supershaun.bikeshop.models.dto.CategoryWithoutChildrenDto;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    List<Category> getAllWithDepth();
    List<CategoryWithoutChildrenDto> getAll();
    CategoryDetailDto getById(Long id);
    List<Item> getItemsById(Long id, Map<String, String> params);
}
