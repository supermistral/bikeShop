package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.models.dto.request.ItemInstanceAdminRequestDto;
import com.supershaun.bikeshop.models.dto.request.ItemInstanceImageAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemInstanceAdminResponseDto;
import com.supershaun.bikeshop.models.dto.response.ItemInstanceImageAdminResponseDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.services.ImageService;
import com.supershaun.bikeshop.services.ItemInstanceService;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/itemInstances", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Tag(name = "Модификации товаров",
        description = "Методы для работы с модификациями товаров (товарными единицами)")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
        @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
})
@SecurityRequirement(name = "JWT Authorization")
public class ItemInstanceController {
    @Autowired
    private ItemInstanceService itemInstanceService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    @Operation(summary = "Получить все модификации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemInstanceAdminResponseDto.class))))
    })
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(itemInstanceService.getAll());
    }

    @PutMapping("{id}")
    @Operation(summary = "Изменить модификацию", description = "Изменить модификацию товара по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Модификация изменена",
                    content = @Content(schema = @Schema(implementation = ItemInstanceAdminResponseDto.ItemInstanceAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Модификация не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemInstanceAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceService.update(id, dto));
    }

    @PostMapping
    @Operation(summary = "Создать модификацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Модификация создана",
                    content = @Content(schema = @Schema(implementation = ItemInstanceAdminResponseDto.ItemInstanceAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<?> create(@RequestBody ItemInstanceAdminRequestDto dto) {
        return ResponseEntity.ok(itemInstanceService.create(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить модификацию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Модификация удалена"),
            @ApiResponse(responseCode = "404", description = "Модификация не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemInstanceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("images")
    @Operation(summary = "Получить все изображения", description = "Получить все изображения модификаций")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemInstanceImageAdminResponseDto.class))))
    })
    public ResponseEntity<?> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImageInstances());
    }

    @PostMapping("images")
    @Operation(summary = "Добавить изображение", description = "Добавить изображение для модификации с указанным id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение добавлено"),
            @ApiResponse(responseCode = "404", description = "Модификация не найдена",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка при чтении файла",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> createImage(@RequestPart("data") ItemInstanceImageAdminRequestDto dto,
                                         @RequestPart("image") MultipartFile image) throws Exception {
        imageService.saveToItemInstance(image.getBytes(), image.getOriginalFilename(), dto.getItemInstanceId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("images/{id}")
    @Operation(summary = "Обновить изображение", description = "Обновить изображение по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение обновлено"),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка при чтении файла",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> updateImage(@PathVariable("id") Long id,
                                         @RequestPart("data") ItemInstanceImageAdminRequestDto dto,
                                         @RequestPart(name = "image", required = false) MultipartFile image) throws Exception {
        imageService.updateImageInstance(id, dto.getItemInstanceId(), image.getBytes(), image.getOriginalFilename());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("images/{id}")
    @Operation(summary = "Удалить изображение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Изображение удалено"),
            @ApiResponse(responseCode = "404", description = "Изображение не найдено",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> deleteImage(@PathVariable("id") Long id) throws Exception {
        imageService.deleteImageInstance(id);
        return ResponseEntity.noContent().build();
    }
}
