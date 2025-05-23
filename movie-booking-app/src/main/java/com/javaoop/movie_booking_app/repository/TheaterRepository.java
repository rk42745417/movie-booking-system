package com.javaoop.movie_booking_app.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.javaoop.movie_booking_app.model.Hall;

public class TheaterRepository {
//    private static final String SELECT_ALL_THEATERS = "SELECT * FROM theaters";
//
//    public List<Hall> getAllTheaters() {
//        List<Hall> halls = new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_booking", "user", "password");
//             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_THEATERS);
//             ResultSet rs = pstmt.executeQuery()) {
//            while (rs.next()) {
//                halls.add(new Hall(rs.getInt("id"), rs.getString("name"), rs.getInt("seat_count")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return halls;
//    }
}

