package com.library.dao;

import com.library.database.DatabaseManager;
import com.library.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Member operations.
 * Handles all database operations for library members.
 */
public class MemberDAO {
    private final DatabaseManager dbManager;
    
    public MemberDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    // Register a new member in our system
    public boolean save(Member member) {
        String sql = "INSERT INTO members (name, email, phone, address, join_date, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getAddress());
            pstmt.setDate(5, Date.valueOf(member.getJoinDate()));
            pstmt.setBoolean(6, member.isActive());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        member.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding member: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get all members from the database
     */
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY name";
        
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Member member = new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getDate("join_date").toLocalDate(),
                    rs.getBoolean("is_active")
                );
                members.add(member);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all members: " + e.getMessage());
        }
        return members;
    }
    
    /**
     * Get a member by ID
     */
    public Member getMemberById(int id) {
        String sql = "SELECT * FROM members WHERE id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getBoolean("is_active")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting member by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Search members by name or email
     */
    public List<Member> searchMembers(String searchTerm) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE name LIKE ? OR email LIKE ? ORDER BY name";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Member member = new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getBoolean("is_active")
                    );
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching members: " + e.getMessage());
        }
        return members;
    }
    
    /**
     * Update a member
     */
    public boolean updateMember(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, phone = ?, address = ?, is_active = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getEmail());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getAddress());
            pstmt.setBoolean(5, member.isActive());
            pstmt.setInt(6, member.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating member: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete a member
     */
    public boolean deleteMember(int id) {
        String sql = "DELETE FROM members WHERE id = ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting member: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get active members only
     */
    public List<Member> getActiveMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE is_active = 1 ORDER BY name";
        
        try (Statement stmt = dbManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Member member = new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getDate("join_date").toLocalDate(),
                    rs.getBoolean("is_active")
                );
                members.add(member);
            }
        } catch (SQLException e) {
            System.err.println("Error getting active members: " + e.getMessage());
        }
        return members;
    }
    
    /**
     * Check if email already exists
     */
    public boolean isEmailExists(String email, int excludeId) {
        String sql = "SELECT COUNT(*) FROM members WHERE email = ? AND id != ?";
        
        try (PreparedStatement pstmt = dbManager.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setInt(2, excludeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
        }
        return false;
    }
}
