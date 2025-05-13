package com.example.chatclient.services;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class NetworkService {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Consumer<String> messageHandler;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(this::receiveMessages).start();
    }

    public void setMessageHandler(Consumer<String> handler) {
        this.messageHandler = handler;
    }

    public boolean authenticate(String username, String password) {
        out.println(String.format("LOGIN:%s:%s", username, password));
        try {
            String response = in.readLine();
            return "AUTH_SUCCESS".equals(response);
        } catch (IOException e) {
            return false;
        }
    }

    public void sendMessage(String sender, String text) {
        out.println(String.format("MSG:%s:%s", sender, text));
    }

    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                if (messageHandler != null) {
                    messageHandler.accept(message);
                }
            }
        } catch (IOException e) {
            System.err.println("Connection lost: " + e.getMessage());
        }
    }
}