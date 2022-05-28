package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.dto.request.ItemSpecificationAdminRequestDto;
import com.supershaun.bikeshop.services.ItemSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itemSpecifications")
public class ItemSpecificationController {
    @Autowired
    private ItemSpecificationService itemSpecificationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(itemSpecificationService.getAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemSpecificationService.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemSpecificationService.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemSpecificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
