package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
