package com.supershaun.bikeshop.controllers;

import com.supershaun.bikeshop.exceptions.ApiError;
import com.supershaun.bikeshop.exceptions.ItemNotFoundException;
import com.supershaun.bikeshop.models.Item;
import com.supershaun.bikeshop.models.dto.ItemDetailDto;
import com.supershaun.bikeshop.models.dto.ItemInstanceDto;
import com.supershaun.bikeshop.models.dto.request.ItemAdminRequestDto;
import com.supershaun.bikeshop.models.dto.response.ItemAdminResponseDto;
import com.supershaun.bikeshop.responses.DefaultMessageEntity;
import com.supershaun.bikeshop.responses.Messages;
import com.supershaun.bikeshop.services.interfaces.IItemService;
import com.supershaun.bikeshop.utils.SpecificationParser;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@Tag(name = "Товары", description = "Методы для работы с товарами")
public class ItemController {
    @Autowired
    private IItemService itemService;

    @GetMapping
    @Operation(summary = "Получить все товары")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Item.class))))
    })
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
    @Operation(summary = "Получить товар", description = "Получить товар по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(schema = @Schema(implementation = ItemDetailDto.class))),
            @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
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
    @Operation(summary = "Получить модификации",
            description = "Получить модификации товаров по параметру строки запроса с id (id=1;2;3)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Операция выполнена успешно",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ItemInstanceDto.class)))),
            @ApiResponse(responseCode = "404", description = "Модификации не найдены",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class)))
    })
    public ResponseEntity<?> getItemInstancesById(@RequestParam("id") String idsString) {
        List<Long> ids = SpecificationParser.parseKeys(idsString).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        if (ids.size() == 0) {
            throw new ItemNotFoundException();
        }

        return ResponseEntity.ok(itemService.getAllInstancesByIds(ids));
    }

    @PutMapping("{id}")
    @Operation(summary = "Изменить товар", description = "Изменить Товар по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар изменена",
                    content = @Content(schema = @Schema(implementation = ItemAdminResponseDto.ItemAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody ItemAdminRequestDto dto) {
        return ResponseEntity.ok(itemService.update(id, dto));
    }

    @PostMapping
    @Operation(summary = "Создать товар")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар создан",
                    content = @Content(schema = @Schema(implementation = ItemAdminResponseDto.ItemAdminDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> create(@RequestBody ItemAdminRequestDto dto) {
        return ResponseEntity.ok(itemService.create(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удалить товар")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Товар удален"),
            @ApiResponse(responseCode = "404", description = "Товар не найден",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = @Content(schema = @Schema(implementation = DefaultMessageEntity.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к ресурсу")
    })
    @SecurityRequirement(name = "JWT Authorization")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
