package com.javaoop.movie_booking_app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.List;

public class MovieInfoJsonToMySQL {
	
	private static boolean movieExists(Connection conn, String title) throws SQLException {
	    String checkSql = "SELECT 1 FROM Movies WHERE title = ?";
	    try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
	        checkStmt.setString(1, title);
	        try (ResultSet rs = checkStmt.executeQuery()) {
	            return rs.next(); // returns true if a row exists
	        }
	    }
	}


    public static void main(String[] args) {
    	String jdbcUrl = "jdbc:mysql://localhost:3306/movie_booking?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "MySQL@root";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connected to database."+conn.getCatalog());

            // Load Movie_info JSON file from resources
            InputStream inputStream = MovieInfoJsonToMySQL.class.getClassLoader().getResourceAsStream("movie_info.json");
            if (inputStream == null) throw new RuntimeException("movie_info.json not found");
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            System.out.println("JSON file loaded successfully");

            // Parse Movie_info JSON array into List<Movie>
            Gson gson = new Gson();
            Type movieListType = new TypeToken<List<Movie>>() {}.getType();
            List<Movie> movies = gson.fromJson(reader, movieListType);

            System.out.println("Found " + movies.size() + " movies. Inserting into database...");

            // Prepare SQL insert
            String sql = "INSERT INTO Movies (title, synopsis, duration_minutes, rating_category, is_active) " +
            "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            	for (Movie movie : movies) {
            	    if (movieExists(conn, movie.title)) {
            	        System.out.println("Skipped (already exists): " + movie.title);
            	        continue;
            	    }

            	    stmt.setString(1, movie.title);
            	    stmt.setString(2, movie.synopsis);
            	    stmt.setInt(3, movie.duration_minutes != null ? movie.duration_minutes : 0);
            	    stmt.setString(4, movie.rating_category);
            	    stmt.setBoolean(5, movie.is_active != null ? movie.is_active : true);

            	    stmt.executeUpdate();
            	    System.out.println("Inserted: " + movie.title);
            	}
//            	for (Movie movie : movies) {
//            	    stmt.setString(1, movie.title);
//            	    stmt.setString(2, movie.synopsis);
//            	    stmt.setInt(3, movie.duration_minutes != null ? movie.duration_minutes : 0);
//
//            	    // Convert rating_category String to enum name expected by DB or just store string
//            	    // Assuming DB column is string, store directly
//            	    stmt.setString(4, movie.rating_category);
//
//            	    // If is_active is null, default to true
//            	    stmt.setBoolean(5, movie.is_active != null ? movie.is_active : true);
//
//            	    try {
//            	        stmt.executeUpdate();
//            	        System.out.println("Inserted: " + movie.title);
//            	    } catch (SQLIntegrityConstraintViolationException e) {
//            	        System.out.println("Skipped duplicate: " + movie.title);
//            	    }
//            	}
            }

            System.out.println("Finished inserting movies.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Inner class matching your JSON structure
    static class Movie {
        Long movie_id;
        String title;
        String synopsis;
        Integer duration_minutes;
        String rating_category;  // keep as String here and convert to enum later
        Boolean is_active;
    }
}

