package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Showtime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeRepository {
    private static final String SELECT_SHOWTIMES_BY_MOVIE = "SELECT * FROM showtimes WHERE movie_id = ?";
    private static final String SELECT_SHOWTIME_BY_ID = "SELECT * FROM showtimes WHERE id = ?";
    private static final String INSERT_SHOWTIME = "INSERT INTO showtimes (movie_id, theater_id, time) VALUES (?, ?, ?)";
    private static final String DELETE_SHOWTIME = "DELETE FROM showtimes WHERE id = ?";

    private static final String URL = "jdbc:mysql://localhost:3306/movie_booking";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    // Get all showtimes for a specific movie
    public List<Showtime> getShowtimesByMovie(int movieId) {
        List<Showtime> showtimes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_SHOWTIMES_BY_MOVIE)) {
            pstmt.setInt(1, movieId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    showtimes.add(new Showtime(rs.getInt("id"), rs.getInt("movie_id"), rs.getInt("theater_id"),
                            rs.getTimestamp("time").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtimes;
    }

    // Find a showtime by its ID
    public Showtime findById(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_SHOWTIME_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Showtime(rs.getInt("id"), rs.getInt("movie_id"), rs.getInt("theater_id"),
                            rs.getTimestamp("time").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save a new showtime to the database and return the showtime object with its generated ID
    public Showtime save(Showtime showtime) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SHOWTIME, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, showtime.getMovieId());
            pstmt.setInt(2, showtime.getTheaterId());
            pstmt.setTimestamp(3, Timestamp.valueOf(showtime.getStartTime()));
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    showtime.setId(rs.getInt(1)); // Set the generated ID to the showtime object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showtime;
    }

    // Delete a showtime by its ID
    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SHOWTIME)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find all showtimes
    public List<Showtime> findAll() {
        // Implement logic to get all showtimes if required
        return new ArrayList<>();
    }
}

