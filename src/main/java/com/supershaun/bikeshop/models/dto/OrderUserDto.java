package com.supershaun.bikeshop.models.dto;

import com.supershaun.bikeshop.models.Order;
import com.supershaun.bikeshop.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderUserDto {
    private Long id;
    private String status;
    private Date createdAt;
    private UserDto user;
    private List<OrderDto.QuantityItemDto> quantityItems;
    private double price;

    public OrderUserDto(Order order) {
        id = order.getId();
        createdAt = order.getCreatedAt();
        status = order.getStatus().name();
        user = new UserDto(order.getUser());
        quantityItems = order.getQuantityItems().stream()
                .map(OrderDto.QuantityItemDto::new)
                .collect(Collectors.toList());
        price = order.getPrice();
    }

    @Getter
    @Setter
    public static class UserDto {
        private String email;
        private String name;

        public UserDto(User user) {
            email = user.getEmail();
            name = user.getName();
        }
    }
}
