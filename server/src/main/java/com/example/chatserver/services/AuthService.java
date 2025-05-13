package com.example.chatserver.services;

import com.example.chatserver.exceptions.AuthenticationException;
import com.example.chatserver.exceptions.UserAlreadyExistsException;
import com.example.chatserver.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class AuthService {
    private final UserRepository userRepo = new UserRepository();

    public void registerUser(String username, String password) throws UserAlreadyExistsException {
        if (userRepo.userExists(username)) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userRepo.saveUser(username, hashedPassword);
    }

    public boolean authenticate(String username, String password) throws AuthenticationException {
        try {
            String storedHash = userRepo.getPasswordHash(username);
            if (storedHash == null) {
                throw new AuthenticationException("User not found");
            }
            return BCrypt.checkpw(password, storedHash);
        } catch (SQLException e) {
            throw new AuthenticationException("Database error");
        }
    }
}