package com.example.chatclient.models;

import lombok.Data;

@Data
public class User {
    private String username;
    private boolean isOnline;

    // Конструктор для удобства
    public User(String username, boolean isOnline) {
        this.username = username;
        this.isOnline = isOnline;
    }
}