package com.example.chatserver.controllers;

import com.example.chatserver.services.AuthService;
import com.example.chatserver.services.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private static final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private final Socket socket;
    private final AuthService authService = new AuthService();
    private final ChatService chatService = new ChatService();
    private final ObjectMapper mapper = new ObjectMapper();
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                Message message = mapper.readValue(inputLine, Message.class);
                if (message.getType().equals("REGISTER")) {
                    handleRegister(message, out);
                } else if (message.getType().equals("LOGIN")) {
                    handleLogin(message, out);
                } else if (message.getType().equals("MESSAGE")) {
                    chatService.broadcastMessage(message);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            if (username != null) {
                clients.remove(username);
                chatService.notifyUserList();
            }
        }
    }

    private void handleRegister(Message message, PrintWriter out) {
        try {
            authService.registerUser(message.getSender(), message.getText());
            out.println(mapper.writeValueAsString(new Message("SERVER", "Registration successful", "SYSTEM")));
        } catch (Exception e) {
            out.println(mapper.writeValueAsString(new Message("SERVER", "Error: " + e.getMessage(), "SYSTEM")));
        }
    }
}