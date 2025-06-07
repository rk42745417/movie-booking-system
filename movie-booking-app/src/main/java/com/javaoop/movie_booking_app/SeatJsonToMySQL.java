package com.javaoop.movie_booking_app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.List;

public class SeatJsonToMySQL {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/movie_booking?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "MySQL@root";

        String[] hallTypes = {"small_room", "big_room"};  // Files and halls to process

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connected to database: " + conn.getCatalog());

            for (String hallType : hallTypes) {
                System.out.println("\nProcessing hall type: " + hallType);

                // 1. Get or create hall, get hall_id
                int hallId = getOrCreateHall(conn, hallType);

                // 2. Load seat JSON file from resources
                String seatJsonFile = hallType + ".json";
                InputStream inputStream = SeatJsonToMySQL.class.getClassLoader().getResourceAsStream(seatJsonFile);
                if (inputStream == null) {
                    System.err.println("ERROR: " + seatJsonFile + " not found in resources.");
                    continue;  // Skip this hall type if JSON missing
                }

                try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                    System.out.println(seatJsonFile + " loaded successfully");

                    // 3. Parse seat JSON into List<SeatData>
                    Gson gson = new Gson();
                    Type seatListType = new TypeToken<List<SeatData>>() {}.getType();
                    List<SeatData> seats = gson.fromJson(reader, seatListType);

                    System.out.println("Found " + seats.size() + " seats. Inserting into database...");

                    // 4. Insert seats into Seats table
                    insertSeats(conn, hallId, seats);

                    // 5. Update total_seats for the hall
                    updateTotalSeats(conn, hallId);

                    System.out.println("Finished processing " + hallType);
                }
            }

            System.out.println("\nAll halls processed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert seats from the list for a given hallId, ignoring duplicates gracefully
    private static void insertSeats(Connection conn, int hallId, List<SeatData> seats) throws SQLException {
        String sql = "INSERT INTO Seats (hall_id, row_char, seat_number_in_row) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (SeatData seat : seats) {
                stmt.setInt(1, hallId);
                stmt.setString(2, seat.row);
                stmt.setInt(3, seat.seatNum);
                try {
                    stmt.executeUpdate();
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Skipped duplicate seat: row " + seat.row + " seatNum " + seat.seatNum);
                }
            }
        }
    }

    // Get hall_id for hallType; insert hall if not found
    private static int getOrCreateHall(Connection conn, String hallTypeRaw) throws SQLException {
        String normalizedHallType = normalizeHallType(hallTypeRaw);

        String selectSql = "SELECT hall_id FROM Halls WHERE hall_type = ?";
        try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setString(1, normalizedHallType);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("hall_id");
                }
            }
        }

        // Hall not found -> insert new hall with zero total seats initially
        String insertSql = "INSERT INTO Halls (hall_type, total_seats) VALUES (?, 0)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, normalizedHallType);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating hall failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newHallId = generatedKeys.getInt(1);
                    System.out.println("Inserted new hall: " + normalizedHallType + " with hall_id " + newHallId);
                    return newHallId;
                } else {
                    throw new SQLException("Creating hall failed, no ID obtained.");
                }
            }
        }
    }

    // Update total_seats in Halls table for given hall_id
    private static void updateTotalSeats(Connection conn, int hallId) throws SQLException {
        String countSeatsSQL = "SELECT COUNT(*) AS seat_count FROM Seats WHERE hall_id = ?";
        int seatCount = 0;

        try (PreparedStatement stmt = conn.prepareStatement(countSeatsSQL)) {
            stmt.setInt(1, hallId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    seatCount = rs.getInt("seat_count");
                }
            }
        }

        String updateHallSQL = "UPDATE Halls SET total_seats = ? WHERE hall_id = ?";
        try (PreparedStatement updateStmt = conn.prepareStatement(updateHallSQL)) {
            updateStmt.setInt(1, seatCount);
            updateStmt.setInt(2, hallId);
            int updated = updateStmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Updated total_seats for hall_id " + hallId + " to " + seatCount);
            } else {
                System.out.println("Failed to update total_seats for hall_id " + hallId);
            }
        }
    }

    // Normalize hall type strings
    private static String normalizeHallType(String hallTypeRaw) {
        String normalized = hallTypeRaw.trim().toLowerCase().replaceAll("\\s+", "_");

        return switch (normalized) {
            case "small", "smallroom", "small_room" -> "small_room";
            case "big", "large", "big_room", "largeroom" -> "big_room";
            default -> throw new IllegalArgumentException("Unknown hall type: " + hallTypeRaw);
        };
    }

    // Inner class for seat JSON data
    static class SeatData {
        String row;     // maps to row_char in DB
        int seatNum;    // maps to seat_number_in_row in DB
        // "region" field from big_room.json ignored here
    }
}



