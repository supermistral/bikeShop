package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.models.dto.request.CategoryAdminRequestDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllWithDepth() {
        return ResponseEntity.ok(categoryService.getAllWithDepth());
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Object category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new DefaultMessageEntity(Messages.CategoryIdNotFound.toString()));
        }
        return ResponseEntity.ok(category);
    }

    @GetMapping("{id}/items")
    public ResponseEntity<?> getItemsById(@PathVariable("id") Long id,
                                          @RequestParam Map<String, String> params) {
        List<Item> items = categoryService.getItemsById(id, params);
        if (items == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new DefaultMessageEntity(Messages.CategoryIdNotFound.toString()));
        }
        return ResponseEntity.ok(items);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestPart("data") CategoryAdminRequestDto dto,
                                    @RequestPart(name = "image", required = false) MultipartFile image) {
        byte[] imageBytes;
        try {
            imageBytes = image.getBytes();
        } catch (Exception ex) {
            imageBytes = null;
        }

        return ResponseEntity.ok(categoryService.update(id, dto, imageBytes));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestPart("data") CategoryAdminRequestDto dto,
                                    @RequestPart("image") MultipartFile image) {
        byte[] imageBytes;
        try {
            imageBytes = image.getBytes();
        } catch (Exception ex) {
            imageBytes = null;
        }

        return ResponseEntity.ok(categoryService.create(dto, imageBytes));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
