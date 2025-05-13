package com.example.chatclient.models;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ChatMessage {
    public enum MessageType {
        PUBLIC, PRIVATE, SYSTEM
    }

    private MessageType type;
    private String sender;
    private String content;
    private String recipient; // Для приватных сообщений
    private LocalDateTime timestamp;

    // Форматированное время для отображения в UI
    public String getFormattedTime() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // Форматированное сообщение для чата
    public String getFormattedMessage() {
        if (type == MessageType.SYSTEM) {
            return "[System] " + content;
        }
        return String.format("[%s] %s: %s", getFormattedTime(), sender, content);
    }
}