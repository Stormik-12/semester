package com.example.chatserver;

import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            var pool = Executors.newCachedThreadPool();
            System.out.println("Server started on port 8080");

            while (true) {
                pool.execute(new ClientHandler(serverSocket.accept()));
            }
        }
    }
}