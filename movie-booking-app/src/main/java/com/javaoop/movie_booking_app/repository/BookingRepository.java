package com.javaoop.movie_booking_app.repository;

import com.javaoop.movie_booking_app.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
//    private static final String INSERT_BOOKING = "INSERT INTO bookings (user_id, session_id, seat_label) VALUES (?, ?, ?)";
//    private static final String SELECT_BOOKINGS_BY_USER = "SELECT * FROM bookings WHERE user_id = ?";
//    private static final String DELETE_BOOKING_BY_ID = "DELETE FROM bookings WHERE id = ?";
//    private static final String SELECT_ALL_BOOKINGS = "SELECT * FROM bookings";
//
//    private static final String URL = "jdbc:mysql://localhost:3306/movie_booking";
//    private static final String USER = "user";
//    private static final String PASSWORD = "password";
//
//    // Save a booking to the database
//    public void saveBooking(Booking booking) {
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(INSERT_BOOKING)) {
//            pstmt.setInt(1, booking.getUserId());
//            pstmt.setInt(2, booking.getSessionId());
//            pstmt.setString(3, booking.getSeatLabel());
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Find all bookings for a specific user
//    public List<Booking> findByUser(User user) {
//        List<Booking> bookings = new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(SELECT_BOOKINGS_BY_USER)) {
//            pstmt.setInt(1, user.getId());
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    bookings.add(new Booking(
//                            rs.getInt("id"),
//                            rs.getInt("user_id"),
//                            rs.getInt("session_id"),
//                            rs.getString("seat_label")
//                    ));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return bookings;
//    }
//
//    // Delete a booking by ID
//    public void deleteById(int id) {
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(DELETE_BOOKING_BY_ID)) {
//            pstmt.setInt(1, id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Save a booking (similar to saveBooking)
//    public Booking save(Booking booking) {
//        saveBooking(booking);  // Using the already implemented saveBooking method
//        return booking;
//    }
//
//    // Get all bookings
//    public List<Booking> findAll() {
//        List<Booking> bookings = new ArrayList<>();
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_BOOKINGS);
//             ResultSet rs = pstmt.executeQuery()) {
//            while (rs.next()) {
//                bookings.add(new Booking(
//                        rs.getInt("id"),
//                        rs.getInt("user_id"),
//                        rs.getInt("session_id"),
//                        rs.getString("seat_label")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return bookings;
//    }
}



