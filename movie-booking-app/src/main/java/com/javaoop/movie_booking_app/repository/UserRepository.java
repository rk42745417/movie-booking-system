package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String INSERT_USER = "INSERT INTO users (email, password, birthdate, is_admin) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    // Get a user by email
    public User getUserByEmail(String email) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_booking", "user", "password");
             PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_EMAIL)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"), 
                                    rs.getDate("birthdate").toLocalDate(), rs.getBoolean("is_admin"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save a new user
    public User saveUser(User user) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_booking", "user", "password");
             PreparedStatement pstmt = conn.prepareStatement(INSERT_USER)) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setDate(3, Date.valueOf(user.getBirthdate()));
            pstmt.setBoolean(4, user.isAdmin());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return user;
    }

    // Find all users
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_booking", "user", "password");
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
                        rs.getDate("birthdate").toLocalDate(), rs.getBoolean("is_admin")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Delete user by ID
    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_booking", "user", "password");
             PreparedStatement pstmt = conn.prepareStatement(DELETE_USER_BY_ID)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



