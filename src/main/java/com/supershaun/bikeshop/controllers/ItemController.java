package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.services.ItemService;
import com.supershaun.bikeshop.services.interfaces.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
