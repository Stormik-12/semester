package com.example.chatclient.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthView {
    public static void show() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(AuthView.class.getResource("/com/example/chatclient/views/auth.fxml"));
            stage.setTitle("Авторизация");
            stage.setScene(new Scene(root, 300, 200));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}