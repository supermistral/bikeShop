package com.supershaun.bikeshop.services;

import com.supershaun.bikeshop.exceptions.UserNotFoundException;
import com.supershaun.bikeshop.models.*;
import com.supershaun.bikeshop.models.dto.OrderDto;
import com.supershaun.bikeshop.models.dto.OrderUserDto;
import com.supershaun.bikeshop.models.dto.QuantityItemDto;
import com.supershaun.bikeshop.models.enums.ERole;
import com.supershaun.bikeshop.models.enums.OrderStatus;
import com.supershaun.bikeshop.repositories.ItemInstanceRepository;
import com.supershaun.bikeshop.repositories.OrderRepository;
import com.supershaun.bikeshop.repositories.QuantityItemRepository;
import com.supershaun.bikeshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuantityItemRepository quantityItemRepository;

    @Autowired
    private ItemInstanceRepository itemInstanceRepository;

    public List<OrderDto> getAllByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        Set<Role> roles = user.getRoles();
        Role role = roles.stream()
                .filter(r -> r.getName() == ERole.ROLE_ADMIN || r.getName() == ERole.ROLE_MANAGER)
                .findAny()
                .orElse(null);

        if (role != null) {
            return orderRepository.findAll().stream()
                    .map(OrderDto::new)
                    .collect(Collectors.toList());
        }

        return orderRepository.findAllByUserEmailOrderByCreatedAtDesc(email).stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    public List<OrderUserDto> getAll() {
        return orderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(OrderUserDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeStatusById(Long id, String statusName) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order [id=" + id + "] not found"));

        OrderStatus status = OrderStatus.valueOf(statusName);
        if (status == null) {
            throw new IllegalStateException("Order status '" + statusName + "' is not valid");
        }

        order.setStatus(status);
        orderRepository.save(order);
    }

    @Transactional
    public OrderDto createByUser(String email, List<QuantityItemDto> items) {
        List<ItemInstance> itemInstances = items.stream()
                .map(item -> itemInstanceRepository.getById(item.getItemInstance().getId()))
                .collect(Collectors.toList());

        for (int i = 0; i < items.size(); ++i) {
            if (itemInstances.get(i).getStock() - items.get(i).getQuantity() < 0) {
                throw new IllegalStateException("Item [id=" + itemInstances.get(i).getId() + "] is not in stock");
            }
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        Order order = new Order(user);
        order.setQuantityItems(
                IntStream.range(0, items.size())
                .mapToObj(index -> {
                    ItemInstance itemInstance = itemInstances.get(index);
                    int newStock = itemInstance.getStock() - items.get(index).getQuantity();

                    itemInstance.setStock(newStock);
                    itemInstanceRepository.save(itemInstance);

                    QuantityItem quantityItem = new QuantityItem(itemInstance, order, items.get(index).getQuantity());
                    quantityItemRepository.save(quantityItem);

                    return quantityItem;
                })
                .collect(Collectors.toSet())
        );

        orderRepository.save(order);

        return new OrderDto(order);
    }
}
