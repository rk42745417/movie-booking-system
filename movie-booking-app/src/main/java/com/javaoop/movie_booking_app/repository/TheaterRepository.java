package com.javaoop.movie_booking_app.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.javaoop.movie_booking_app.model.Theater;

public class TheaterRepository {
    private static final String SELECT_ALL_THEATERS = "SELECT * FROM theaters";

    public List<Theater> getAllTheaters() {
        List<Theater> theaters = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_booking", "user", "password");
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_THEATERS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                theaters.add(new Theater(rs.getInt("id"), rs.getString("name"), rs.getInt("seat_count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return theaters;
    }
}

