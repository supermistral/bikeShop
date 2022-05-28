package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.dto.request.ItemInstanceAdminRequestDto;
import com.supershaun.bikeshop.models.dto.request.ItemInstanceImageAdminRequestDto;
import com.supershaun.bikeshop.models.dto.request.ItemInstanceSpecificationAdminRequestDto;
import com.supershaun.bikeshop.services.ImageService;
import com.supershaun.bikeshop.services.ItemInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/itemInstances")
@CrossOrigin
public class ItemInstanceController {
    @Autowired
    private ItemInstanceService itemInstanceService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(itemInstanceService.getAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemInstanceAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceService.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemInstanceAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceService.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemInstanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("images")
    public ResponseEntity<?> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImageInstances());
    }

    @PostMapping("images")
    public ResponseEntity<?> createImage(@RequestPart("data") ItemInstanceImageAdminRequestDto dto,
                                         @RequestPart("image") MultipartFile image) throws Exception {
        imageService.saveToItemInstance(image.getBytes(), image.getOriginalFilename(), dto.getItemInstanceId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("images/{id}")
    public ResponseEntity<?> updateImage(@PathVariable("id") Long id,
                                         @RequestPart("data") ItemInstanceImageAdminRequestDto dto,
                                         @RequestPart(name = "image", required = false) MultipartFile image) throws Exception {
        imageService.updateImageInstance(id, dto.getItemInstanceId(), image.getBytes(), image.getOriginalFilename());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable("id") Long id) throws Exception {
        imageService.deleteImageInstance(id);
        return ResponseEntity.ok().build();
    }
}
