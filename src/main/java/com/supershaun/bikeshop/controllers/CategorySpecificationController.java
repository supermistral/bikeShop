package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.models.dto.request.CategorySpecificationAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.CategorySpecificationAdminResponseDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.services.CategorySpecificationService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/categorySpecifications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Характеристики категорий", description = "Методы для работы с характеристиками категорий")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
        @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
})
@SecurityRequirement(name = "JWT Authorization")
public class CategorySpecificationController {
    @Autowired
    private CategorySpecificationService categorySpecificationService;

    @GetMapping
    @Operation(summary = "Получить все характеристики")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(schema = @Schema(implementation = CategorySpecificationAdminResponseDto.class)))
    })
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categorySpecificationService.getAll());
    }

    @PutMapping("{id}")
    @Operation(summary = "Изменить характеристику", description = "Изменить характеристику категории по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Характеристика изменена",
                    content = @Content(schema = @Schema(implementation = CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Характеристика не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody CategorySpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(categorySpecificationService.update(id, dto));
    }

    @PostMapping
    @Operation(summary = "Создать характеристику")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Характеристика создана",
                    content = @Content(schema = @Schema(implementation = CategorySpecificationAdminResponseDto.CategorySpecificationAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> create(@RequestBody CategorySpecificationAdminRequestDto dto) {
        return ResponseEntity.ok(categorySpecificationService.create(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить характеристику")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Характеристика удалена"),
            @ApiResponse(responseCode = "404", description = "Характеристика не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity delete(@PathVariable("id") Long id) {
        categorySpecificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
