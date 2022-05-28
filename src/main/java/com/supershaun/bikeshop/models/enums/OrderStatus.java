package com.supershaun.bikeshop.models.enums;

public enum OrderStatus {
    Confirmed("Подтвержден"),
    Created("Создан"),
    InProcessing("В обработке"),
    Completed("Выполнен");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public static OrderStatus valueOfName(String name) {
        for (OrderStatus status : values()) {
            if (status.name.equals(name))
                return status;
        }
        return null;
    }
}
