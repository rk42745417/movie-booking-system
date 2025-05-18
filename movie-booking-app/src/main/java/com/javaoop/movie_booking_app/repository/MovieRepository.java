package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private static final String SELECT_ALL_MOVIES = "SELECT * FROM movies";
    private static final String SELECT_MOVIE_BY_ID = "SELECT * FROM movies WHERE id = ?";
    private static final String INSERT_MOVIE = "INSERT INTO movies (name, synopsis, duration, rating) VALUES (?, ?, ?, ?)";
    private static final String DELETE_MOVIE = "DELETE FROM movies WHERE id = ?";

    private static final String URL = "jdbc:mysql://localhost:3306/movie_booking";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    // Get all movies
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_MOVIES);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(rs.getInt("id"), rs.getString("name"), rs.getString("synopsis"), rs.getInt("duration"), rs.getDouble("rating")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Find a movie by its ID
    public Movie findById(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_MOVIE_BY_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Movie(rs.getInt("id"), rs.getString("name"), rs.getString("synopsis"), rs.getInt("duration"), rs.getDouble("rating"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save a new movie (inserts into the DB)
    public Movie save(Movie movie) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_MOVIE, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, movie.getName());
            pstmt.setString(2, movie.getSynopsis());
            pstmt.setInt(3, movie.getDuration());
            pstmt.setDouble(4, movie.getRating());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    movie.setId(rs.getInt(1));  // set generated ID to the Movie object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movie;
    }

    // Delete a movie by its ID
    public void deleteById(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(DELETE_MOVIE)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all movies (returning a list of movies)
    public List<Movie> findAll() {
        return getAllMovies();  // Simply return the result from getAllMovies method
    }
}



