package com.example.chatclient.models;

import lombok.Data;

@Data
public class ServerResponse {
    public enum Status {
        SUCCESS, ERROR
    }

    private Status status;
    private String message;
    private Object data; // Дополнительные данные

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}