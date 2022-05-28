package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.dto.request.UserAdminRequestDto;
import com.supershaun.bikeshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody UserAdminRequestDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserAdminRequestDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
