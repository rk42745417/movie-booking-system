/**
 * 
 */
package repository;

import model.CourseStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;

/**
 * 
 */
public class CourseRepository {
    private static final String INSERT = "INSERT INTO courses (title, description, capacity, coach_id) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM courses";
    private static final String DELETE_BY_ID = "DELETE FROM courses WHERE id = ?";
    private static final String SELECT_BY_COACH = "SELECT * FROM courses WHERE coach_id = ?";

    public CourseStatus save(CourseStatus course) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getDescription());
            pstmt.setInt(3, course.getCapacity());
            pstmt.setLong(4, course.getCoachId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return course;
    }

    public List<CourseStatus> findAll() {
        List<CourseStatus> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                courses.add(new CourseStatus(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("capacity"),
                    rs.getLong("coach_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<CourseStatus> findByCoachId(long coachId) {
        List<CourseStatus> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_COACH)) {
            pstmt.setLong(1, coachId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(new CourseStatus(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("capacity"),
                        rs.getLong("coach_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
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
