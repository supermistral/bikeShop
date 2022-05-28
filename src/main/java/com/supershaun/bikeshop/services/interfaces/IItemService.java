package com.supershaun.bikeshop.services.interfaces;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.models.dto.ItemInstanceDto;
import com.supershaun.bikeshop.models.dto.request.ItemAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemAdminResponseDto;
import com.supershaun.bikeshop.models.dto.response.ItemInstanceAdminResponseDto;

import java.util.List;
import java.util.Map;

public interface IItemService {
    ItemAdminResponseDto getAll();
    List<Item> getByIds(List<Long> ids);
    ItemDetailDto getById(Long id);
    List<ItemInstanceDto> getAllInstancesByIds(List<Long> ids);
    ItemAdminResponseDto.ItemAdminDto update(Long id, ItemAdminRequestDto dto);
    ItemAdminResponseDto.ItemAdminDto create(ItemAdminRequestDto dto);
    void delete(Long id);
}
