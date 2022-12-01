package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.models.dto.OrderDto;
import com.supershaun.bikeshop.models.dto.QuantityItemDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.security.jwt.JwtUtils;
import com.supershaun.bikeshop.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Tag(name = "Заказы", description = "Методы для работы с заказами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
})
@SecurityRequirement(name = "JWT Authorization")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    @Operation(summary = "Получить все заказы пользователя",
            description = "Получить все заказы пользователя по его id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))))
    })
    public ResponseEntity<?> getAllByUser(@RequestHeader("Authorization") String authorizationHeader) {
        String email = jwtUtils.getUserNameFromAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(orderService.getAllByUser(email));
    }

    @PutMapping("{id}/status")
    @Operation(summary = "Изменить статус", description = "Изменить статус заказа с указанным id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус заказа изменен",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "400", description = "Некорректный статус заказа",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long id, @RequestParam("name") String name) {
        orderService.changeStatusById(id, name);
        return ResponseEntity.ok(new DefaultMessageEntity(Messages.OrderStatusSuccessfully.toString()));
    }

    @PostMapping
    @Operation(summary = "Создать заказ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ создан",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> create(@RequestHeader("Authorization") String authorizationHeader,
                                    @RequestBody List<QuantityItemDto> quantityItemDtos) {
        String email = jwtUtils.getUserNameFromAuthorizationHeader(authorizationHeader);
        return ResponseEntity.ok(orderService.createByUser(email, quantityItemDtos));
    }
}
