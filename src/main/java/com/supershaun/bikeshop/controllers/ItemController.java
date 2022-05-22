package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.services.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@CrossOrigin
public class ItemController {
    @Autowired
    private IItemService itemService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(itemService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getItemById(@PathVariable("id") Long id) {
        ItemDetailDto item = itemService.getById(id);
        if (item == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new DefaultMessageEntity(Messages.CategoryIdNotFound.toString()));
        }
        return ResponseEntity.ok(item);
    }
}
