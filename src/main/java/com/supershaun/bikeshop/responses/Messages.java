package com.supershaun.bikeshop.responses;

public enum Messages {
    CategoryIdNotFound("Category with that id not found"),
    ItemIdNotFound("Item with that id not found");

    private final String name;

    Messages(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
