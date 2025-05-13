package com.example.chatserver.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private String type; // REGISTER, LOGIN, MESSAGE, USER_LIST
    private String sender;
    private String text;
    private String recipient;
    private LocalDateTime timestamp = LocalDateTime.now();
}