package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.models.dto.QuantityItemDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.security.jwt.JwtUtils;
import com.supershaun.bikeshop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> getAllByUser(@RequestHeader("Authorization") String authorizationHeader) {
        String email = jwtUtils.getUserNameFromAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(orderService.getAllByUser(email));
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PutMapping("{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, @RequestParam("name") String name) {
        orderService.changeStatusById(id, name);
        return ResponseEntity.ok(new DefaultMessageEntity(Messages.OrderStatusSuccessfully.toString()));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authorizationHeader,
                                    @RequestBody List<QuantityItemDto> quantityItemDtos) {
        String email = jwtUtils.getUserNameFromAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(orderService.createByUser(email, quantityItemDtos));
    }
}
