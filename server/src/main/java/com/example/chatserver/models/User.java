package com.example.chatserver.models;

import lombok.Data;

@Data
public class User {
    private String username;
    private String passwordHash;
}