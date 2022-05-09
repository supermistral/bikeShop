package com.supershaun.bikeshop.responses;

public enum Messages {
     CategoryIdNotFound("Category with that id not found");

    private String name;

    Messages(String name) {
        this.name = name();
    }
}
