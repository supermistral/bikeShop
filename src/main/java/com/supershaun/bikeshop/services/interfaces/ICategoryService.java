package com.supershaun.bikeshop.services.interfaces;

import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.ItemInstance;
import com.supershaun.bikeshop.models.dto.CategoryDetailDto;
import com.supershaun.bikeshop.models.dto.CategoryWithoutChildrenDto;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.models.dto.request.CategoryAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.CategoryAdminResponseDto;

import java.util.List;
import java.util.Map;

public interface ICategoryService {
    List<Category> getAllWithDepth();
    List<CategoryAdminResponseDto> getAll();
    CategoryDetailDto getById(Long id);
    List<Item> getItemsById(Long id, Map<String, String> params);
    CategoryAdminResponseDto update(Long id, CategoryAdminRequestDto dto, byte[] image);
    CategoryAdminResponseDto create(CategoryAdminRequestDto dto, byte[] image);
    void delete(Long id);
}
