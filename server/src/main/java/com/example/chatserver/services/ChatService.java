package com.example.chatserver.services;

import com.example.chatserver.repositories.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {
    private static final ConcurrentHashMap<String, PrintWriter> clients = new ConcurrentHashMap<>();
    private final MessageRepository messageRepo = new MessageRepository();
    private final ObjectMapper mapper = new ObjectMapper();

    public void addClient(String username, PrintWriter writer) {
        clients.put(username, writer);
        notifyUserList();
    }

    public void broadcastMessage(Message message) {
        try {
            messageRepo.saveMessage(message);
            String json = mapper.writeValueAsString(message);
            clients.forEach((user, writer) -> writer.println(json));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyUserList() {
        Message msg = new Message();
        msg.setType("USER_LIST");
        msg.setText(String.join(",", clients.keySet()));
        broadcastMessage(msg);
    }
}