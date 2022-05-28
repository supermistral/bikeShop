package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.dto.request.CategorySpecificationAdminRequestDto;
import com.supershaun.bikeshop.services.CategorySpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorySpecifications")
public class CategorySpecificationController {
    @Autowired
    private CategorySpecificationService categorySpecificationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categorySpecificationService.getAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody CategorySpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(categorySpecificationService.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategorySpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(categorySpecificationService.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        categorySpecificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
