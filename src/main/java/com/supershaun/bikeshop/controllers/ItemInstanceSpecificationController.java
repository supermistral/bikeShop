package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.dto.request.ItemInstanceSpecificationAdminRequestDto;
import com.supershaun.bikeshop.models.dto.request.UserAdminRequestDto;
import com.supershaun.bikeshop.services.ItemInstanceSpecificationService;
import com.supershaun.bikeshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itemInstanceSpecifications")
public class ItemInstanceSpecificationController {
    @Autowired
    private ItemInstanceSpecificationService itemInstanceSpecificationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(itemInstanceSpecificationService.getAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemInstanceSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceSpecificationService.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemInstanceSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceSpecificationService.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemInstanceSpecificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
