package com.supershaun.bikeshop.models.enums;

public enum OrderStatus {
    NotPaid("Not paid"),
    Paid("Paid"),
    Confirmed("Confirmed"),
    Collected("Collected"),
    Received("Received");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
