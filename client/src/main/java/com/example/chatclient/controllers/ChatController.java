package com.example.chatclient.controllers;

import com.example.chatclient.services.NetworkService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {
    @FXML private TextArea chatArea;
    @FXML private TextField messageField;
    @FXML private ListView<String> userList;
    private NetworkService networkService;
    private String username;

    public void initialize(NetworkService networkService, String username) {
        this.networkService = networkService;
        this.username = username;
        networkService.setMessageHandler(msg -> {
            chatArea.appendText(msg + "\n");
        });
    }

    @FXML
    private void sendMessage() {
        String text = messageField.getText();
        if (!text.isEmpty()) {
            networkService.sendMessage(username, text);
            messageField.clear();
        }
    }
}