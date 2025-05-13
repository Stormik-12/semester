package com.example.chatclient.views;

import com.example.chatclient.services.NetworkService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatView {
    public static void show(String username, NetworkService networkService) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(ChatView.class.getResource("/com/example/chatclient/views/chat.fxml"));
            Parent root = loader.load();

            ChatController controller = loader.getController();
            controller.initialize(networkService, username);

            stage.setTitle("Чат: " + username);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}