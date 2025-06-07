package com.javaoop.movie_booking_app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class SessionTimeJsonToMySQL {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/movie_booking?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "MySQL@root";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
            System.out.println("Connected to database: " + conn.getCatalog());

            // STEP 1: Ensure halls exist and insert seats
            ensureHallsExist(conn);
            loadAndInsertHallSeats(conn);

            // STEP 2: Load session_time.json and insert into Showtimes
            InputStream inputStream = SessionTimeJsonToMySQL.class.getClassLoader().getResourceAsStream("session_time.json");
            if (inputStream == null) throw new RuntimeException("session_time.json not found");
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            System.out.println("session_time.json loaded");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<ShowtimeJson>>() {}.getType();
            List<ShowtimeJson> showtimes = gson.fromJson(reader, listType);
            System.out.println("Found " + showtimes.size() + " showtimes. Inserting...");

            for (ShowtimeJson st : showtimes) {
                int hallId = getHallIdByType(conn, st.hallType);
                if (hallId == -1) {
                    System.err.println("Unknown hallType '" + st.hallType + "' for showtime: skipping.");
                    continue;
                }
                Timestamp start = toTimestamp(st.startTime);
                Timestamp end = toTimestamp(st.endTime);

                String sql = "INSERT INTO Showtimes (movie_id, hall_id, start_time, end_time) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, st.movieId);
                    stmt.setInt(2, hallId);
                    stmt.setTimestamp(3, start);
                    stmt.setTimestamp(4, end);
                    stmt.executeUpdate();
                    System.out.println("Inserted showtime: movieId=" + st.movieId + ", hallType=" + st.hallType + ", start=" + start);
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Skipped duplicate showtime: movieId=" + st.movieId + ", hallType=" + st.hallType + ", start=" + start);
                }
            }

            System.out.println("All showtimes inserted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ======== Hall management ========

    /** Ensure halls with hall_type 'small_room' and 'big_room' exist; create if missing */
    private static void ensureHallsExist(Connection conn) throws SQLException {
        String[] hallTypes = {"small_room", "big_room"};
        for (String hallType : hallTypes) {
            int id = getHallIdByType(conn, hallType);
            if (id == -1) {
                // Insert hall with total_seats = 0 for now (will update after seats insertion)
                String insertHall = "INSERT INTO Halls (hall_type, total_seats) VALUES (?, 0)";
                try (PreparedStatement ps = conn.prepareStatement(insertHall)) {
                    ps.setString(1, hallType);
                    ps.executeUpdate();
                    System.out.println("Inserted hall type: " + hallType);
                }
            }
        }
    }

    /** Return hall_id by hall_type; -1 if not found */
    private static int getHallIdByType(Connection conn, String hallType) throws SQLException {
        String query = "SELECT hall_id FROM Halls WHERE hall_type = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, hallType);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("hall_id");
                else return -1;
            }
        }
    }

    // ======== Seat insertion ========

    private static void loadAndInsertHallSeats(Connection conn) {
        loadSeatsFromFile(conn, "big_room.json", "big_room");
        loadSeatsFromFile(conn, "small_room.json", "small_room");
    }

    private static void loadSeatsFromFile(Connection conn, String filename, String hallType) {
        try {
            InputStream seatStream = SessionTimeJsonToMySQL.class.getClassLoader().getResourceAsStream(filename);
            if (seatStream == null) throw new RuntimeException(filename + " not found");
            InputStreamReader seatReader = new InputStreamReader(seatStream, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<SimpleSeat>>() {}.getType();
            List<SimpleSeat> seats = gson.fromJson(seatReader, listType);

            int hallId = getHallIdByType(conn, hallType);
            if (hallId == -1) {
                System.err.println("Hall type '" + hallType + "' not found. Cannot insert seats.");
                return;
            }

            String insert = "INSERT INTO Seats (hall_id, row_char, seat_number_in_row) VALUES (?, ?, ?)";

            int inserted = 0;
            for (SimpleSeat seat : seats) {
                try (PreparedStatement ps = conn.prepareStatement(insert)) {
                    ps.setInt(1, hallId);
                    ps.setString(2, seat.row);
                    ps.setInt(3, seat.seatNum);
                    ps.executeUpdate();
                    inserted++;
                } catch (SQLIntegrityConstraintViolationException e) {
                    // Duplicate seat: ignore
                }
            }

            System.out.println("Inserted " + inserted + " seats into hallType=" + hallType);
            updateTotalSeats(conn, hallId);
        } catch (Exception e) {
            System.err.println("Failed to load or insert seats from " + filename);
            e.printStackTrace();
        }
    }

    private static void updateTotalSeats(Connection conn, int hallId) throws SQLException {
        String sql = """
            UPDATE Halls
            SET total_seats = (SELECT COUNT(*) FROM Seats WHERE hall_id = ?)
            WHERE hall_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, hallId);
            stmt.setInt(2, hallId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Updated total_seats for hall_id=" + hallId);
            }
        }
    }

    // ======== Timestamp helper ========
    private static Timestamp toTimestamp(List<Integer> dateTime) {
        if (dateTime == null || dateTime.size() < 5) throw new IllegalArgumentException("Invalid dateTime array");
        LocalDateTime ldt = LocalDateTime.of(dateTime.get(0), dateTime.get(1), dateTime.get(2), dateTime.get(3), dateTime.get(4));
        return Timestamp.valueOf(ldt);
    }

    // ======== Data classes for JSON ========
    static class SimpleSeat {
        String row;      // e.g. "A", "B"
        int seatNum;     // seat number in row, e.g. 1, 2, 10
    }

    static class ShowtimeJson {
        int showtimeId;
        int movieId;
        String title;
        List<Integer> startTime;
        List<Integer> endTime;
        String hallType;
    }
}
