package com.javaoop.movie_booking_app.service;

import com.javaoop.movie_booking_app.model.User;
import model.Ticket;
import database.Database;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

public class UserService {
    public boolean register(String email, String password, String birthdayStr) {
        if (Database.users.values().stream().anyMatch(u -> u.email.equals(email))) return false;

        LocalDate birthday;
        try {
            birthday = LocalDate.parse(birthdayStr); // 格式 yyyy-MM-dd
        } catch (DateTimeParseException e) {
            return false;
        }

        String userId = UUID.randomUUID().toString();
        String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        Database.users.put(userId, new User(userId, email, encryptedPassword, birthday));
        return true;
    }

    public User login(String email, String password) {
        String encrypted = Base64.getEncoder().encodeToString(password.getBytes());
        return Database.users.values().stream()
                .filter(u -> u.email.equals(email) && u.password.equals(encrypted))
                .findFirst().orElse(null);
    }

    public List<Ticket> getUserTickets(String userId) {
        return Database.tickets.values().stream()
                .filter(t -> t.userId.equals(userId))
                .collect(Collectors.toList());
    }
}

