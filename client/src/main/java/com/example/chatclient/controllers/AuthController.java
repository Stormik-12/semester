package com.example.chatclient.controllers;

import com.example.chatclient.services.NetworkService;
import com.example.chatclient.views.ChatView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private NetworkService networkService = new NetworkService();

    @FXML
    private void handleLogin() {
        try {
            networkService.connect("localhost", 8080);
            boolean success = networkService.authenticate(
                    usernameField.getText(),
                    passwordField.getText()
            );
            if (success) {
                ChatView.show(usernameField.getText(), networkService);
            } else {
                showAlert("Error", "Invalid credentials");
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}