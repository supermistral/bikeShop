package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.models.dto.request.ItemSpecificationAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemSpecificationAdminResponseDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.services.ItemSpecificationService;
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

@RestController
@RequestMapping(value = "/api/itemSpecifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Характеристики товаров", description = "Методы для работы с товарными характеристиками")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
        @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
})
@SecurityRequirement(name = "JWT Authorization")
public class ItemSpecificationController {
    @Autowired
    private ItemSpecificationService itemSpecificationService;

    @GetMapping
    @Operation(summary = "Получить все характеристики")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemSpecificationAdminResponseDto.class))))
    })
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(itemSpecificationService.getAll());
    }

    @PutMapping("{id}")
    @Operation(summary = "Изменить характеристику", description = "Изменить товарную характеристику по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Характеристика изменена",
                    content = @Content(schema = @Schema(implementation = ItemSpecificationAdminResponseDto.ItemSpecificationAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemSpecificationService.update(id, dto));
    }

    @PostMapping
    @Operation(summary = "Создать характеристику")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Характеристика создана",
                    content = @Content(schema = @Schema(implementation = ItemSpecificationAdminResponseDto.ItemSpecificationAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> create(@RequestBody ItemSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemSpecificationService.create(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить характеристику")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Характеристика удалена"),
            @ApiResponse(responseCode = "404", description = "Характеристика не найдена")
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemSpecificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
