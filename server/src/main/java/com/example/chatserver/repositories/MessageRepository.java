package com.example.chatserver.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageRepository {
    public void saveMessage(Message message) throws SQLException {
        String sql = "INSERT INTO messages(sender, recipient, text) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, message.getSender());
            pstmt.setString(2, message.getRecipient());
            pstmt.setString(3, message.getText());
            pstmt.executeUpdate();
        }
    }

    public List<Message> getPrivateMessages(String user1, String user2) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE (sender=? AND recipient=?) OR (sender=? AND recipient=?) ORDER BY timestamp";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:chat.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user1);
            pstmt.setString(2, user2);
            pstmt.setString(3, user2);
            pstmt.setString(4, user1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Message msg = new Message();
                msg.setSender(rs.getString("sender"));
                msg.setRecipient(rs.getString("recipient"));
                msg.setText(rs.getString("text"));
                messages.add(msg);
            }
        }
        return messages;
    }
}