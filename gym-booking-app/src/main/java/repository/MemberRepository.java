/**
 * 
 */
package repository;

import model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;

/**
 * 
 */
public class MemberRepository {
    private static final String INSERT = "INSERT INTO members (name, email, password) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM members";
    private static final String DELETE_BY_ID = "DELETE FROM members WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM members WHERE email = ?";

    public Member save(Member member) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return member;
    }

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                members.add(new Member(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public Member findByEmail(String email) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_EMAIL)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
