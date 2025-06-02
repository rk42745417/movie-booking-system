/**
 * 
 */
package repository;

import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;

/**
 * 
 */
public class BookingRepository {
    private static final String INSERT_BOOKING = "INSERT INTO bookings (member_id, course_id) VALUES (?, ?)";
    private static final String SELECT_ALL_BOOKINGS = "SELECT * FROM bookings";
    private static final String DELETE_BOOKING_BY_ID = "DELETE FROM bookings WHERE id = ?";
    private static final String SELECT_BY_MEMBER = "SELECT * FROM bookings WHERE member_id = ?";

    public void save(Booking booking) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_BOOKING)) {
            pstmt.setLong(1, booking.getMemberId());
            pstmt.setLong(2, booking.getCourseId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_BOOKINGS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Booking(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("course_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteById(long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_BOOKING_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Booking> findByMemberId(long memberId) {
        List<Booking> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_MEMBER)) {
            pstmt.setLong(1, memberId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Booking(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getLong("course_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
