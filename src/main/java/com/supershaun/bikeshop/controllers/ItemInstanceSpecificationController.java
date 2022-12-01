package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.models.dto.request.ItemInstanceSpecificationAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemInstanceSpecificationAdminResponseDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.services.ItemInstanceSpecificationService;
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
@RequestMapping(value = "/api/itemInstanceSpecifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Характеристики модификаций товаров",
        description = "Методы для работы с характеристиками модификаций товаров")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
        @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
})
@SecurityRequirement(name = "JWT Authorization")
public class ItemInstanceSpecificationController {
    @Autowired
    private ItemInstanceSpecificationService itemInstanceSpecificationService;

    @GetMapping
    @Operation(summary = "Получить все характеристики")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemInstanceSpecificationAdminResponseDto.class))))
    })
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(itemInstanceSpecificationService.getAll());
    }

    @PutMapping("{id}")
    @Operation(summary = "Изменить характеристику", description = "Изменить характеристику модификации товара по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Характеристика изменена",
                    content = @Content(schema = @Schema(implementation = ItemInstanceSpecificationAdminResponseDto.ItemInstanceSpecificationAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Характеристика не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemInstanceSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceSpecificationService.update(id, dto));
    }

    @PostMapping
    @Operation(summary = "Создать характеристику")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Характеристика создана",
                    content = @Content(schema = @Schema(implementation = ItemInstanceSpecificationAdminResponseDto.ItemInstanceSpecificationAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> create(@RequestBody ItemInstanceSpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceSpecificationService.create(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить характеристику")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Характеристика удалена"),
            @ApiResponse(responseCode = "404", description = "Характеристика не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemInstanceSpecificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
