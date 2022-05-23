package com.supershaun.bikeshop.responses;

public enum Messages {
    CategoryIdNotFound("Category with that id not found"),
    ItemIdNotFound("Item with that id not found"),
    UserRegistredSuccessfully("User registered successfully"),
    LogoutSuccessfully("User logout successfully"),
    EmailAlreadyInUse("Email already in use"),
    RoleNotFoundException("Role not found");

    private final String name;

    Messages(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
