package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ItemNotFoundException;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.models.dto.request.ItemAdminRequestDto;
import com.supershaun.bikeshop.models.dto.request.ItemSpecificationAdminRequestDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.services.interfaces.IItemService;
import com.supershaun.bikeshop.utils.SpecificationParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
@CrossOrigin
public class ItemController {
    @Autowired
    private IItemService itemService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> params) {
        String idsValue = params.get("id");
        if (idsValue != null) {
            List<Long> ids = SpecificationParser.parseKeys(idsValue).stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(itemService.getByIds(ids));
        }
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

    @GetMapping("/instances")
    public ResponseEntity<?> getItemInstanceById(@RequestParam("id") String idsString) {
        List<Long> ids = SpecificationParser.parseKeys(idsString).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        if (ids.size() == 0) {
            throw new ItemNotFoundException();
        }

        return ResponseEntity.ok(itemService.getAllInstancesByIds(ids));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemAdminRequestDto dto) {
        return ResponseEntity.ok(itemService.update(id, dto));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemAdminRequestDto dto) {
        return ResponseEntity.ok(itemService.create(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
