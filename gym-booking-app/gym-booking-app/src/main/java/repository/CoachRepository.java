/**
 * 
 */
package repository;

import model.Coach;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
/**
 * 
 */
public class CoachRepository {
    private static final String INSERT = "INSERT INTO coaches (name, specialty) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM coaches";
    private static final String DELETE_BY_ID = "DELETE FROM coaches WHERE id = ?";

    public Coach save(Coach coach) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
            pstmt.setString(1, coach.getName());
            pstmt.setString(2, coach.getSpecialty());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return coach;
    }

    public List<Coach> findAll() {
        List<Coach> coaches = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                coaches.add(new Coach(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("specialty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coaches;
    }

    public void deleteById(long id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_BY_ID)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
