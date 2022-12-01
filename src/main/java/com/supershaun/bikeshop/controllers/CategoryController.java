package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.models.Category;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.CategoryDetailDto;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.models.dto.request.CategoryAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.CategoryAdminResponseDto;
import com.supershaun.bikeshop.models.dto.response.CategorySpecificationAdminResponseDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.services.interfaces.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/category", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Tag(name = "Категории", description = "Методы для работы с категориями")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    @Operation(summary = "Получить иерархию категорий", description = "Получить все категории в иерархической структуре")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Category.class))))
    })
    public ResponseEntity<?> getAllWithDepth() {
        return ResponseEntity.ok(categoryService.getAllWithDepth());
    }

    @GetMapping("all")
    @Operation(summary = "Получить категории", description = "Получить все категории в нормальной форме")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryAdminResponseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("{id}")
    @Operation(summary = "Получить категорию", description = "Получить категорию по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(schema = @Schema(implementation = CategoryDetailDto.class))),
            @ApiResponse(responseCode = "404", description = "Категория не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Object category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new DefaultMessageEntity(Messages.CategoryIdNotFound.toString()));
        }
        return ResponseEntity.ok(category);
    }

    @GetMapping("{id}/items")
    @Operation(summary = "Получить товары", description = "Получить товар категории по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Item.class)))),
            @ApiResponse(responseCode = "404", description = "Категория не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> getItemsById(@PathVariable("id") Long id,
                                          @RequestParam Map<String, String> params) {
        List<Item> items = categoryService.getItemsById(id, params);
        if (items == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new DefaultMessageEntity(Messages.CategoryIdNotFound.toString()));
        }
        return ResponseEntity.ok(items);
    }

    @PutMapping(value = "{id}")
    @Operation(summary = "Изменить категорию", description = "Изменить категорию по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория изменена",
                    content = @Content(schema = @Schema(implementation = CategoryAdminResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка при чтении файла",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestPart("data") CategoryAdminRequestDto dto,
                                    @RequestPart(name = "image", required = false) MultipartFile image) throws Exception {
        byte[] imageBytes = image.getBytes();
        return ResponseEntity.ok(categoryService.update(id, dto, imageBytes));
    }

    @PostMapping
    @Operation(summary = "Создать категорию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория создана",
                    content = @Content(schema = @Schema(implementation = CategoryAdminResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> create(@RequestPart("data") CategoryAdminRequestDto dto,
                                    @RequestPart("image") MultipartFile image) throws Exception {
        byte[] imageBytes = image.getBytes();
        return ResponseEntity.ok(categoryService.create(dto, imageBytes));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить категорию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Категория удалена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
