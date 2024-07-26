package com.gsm.dao;

import com.gsm.model.Member;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private String jdbcURL = "jdbc:derby://localhost:1527/GymManagementSystem";
    private String jdbcUsername = "app";
    private String jdbcPassword = "app";
    private Connection jdbcConnection;

    public MemberDAO() {
        // Initialize the connection if needed
    }

    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
                jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                System.out.println("Database connection established.");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver not found.", e);
            }
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
            System.out.println("Database connection closed.");
        }
    }

    public void insertMember(Member member) throws SQLException {
        String sql = "INSERT INTO members (fullname, dob, gender, contact_number, email, address, country, postcode, occupation, membership_type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect();
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, member.getFullname());
            statement.setDate(2, member.getDob());
            statement.setString(3, member.getGender());
            statement.setString(4, member.getContactNumber());
            statement.setString(5, member.getEmail());
            statement.setString(6, member.getAddress());
            statement.setString(7, member.getCountry());
            statement.setString(8, member.getPostcode());
            statement.setString(9, member.getOccupation());
            statement.setInt(10, member.getMembershipTypeId());

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            disconnect();
        }
    }

    public Member getMemberById(int id) throws SQLException {
        Member member = null;
        String sql = "SELECT * FROM members WHERE id = ?";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String fullname = resultSet.getString("fullname");
                    java.sql.Date dob = resultSet.getDate("dob");
                    String gender = resultSet.getString("gender");
                    String contactNumber = resultSet.getString("contact_number");
                    String email = resultSet.getString("email");
                    String address = resultSet.getString("address");
                    String country = resultSet.getString("country");
                    String postcode = resultSet.getString("postcode");
                    String occupation = resultSet.getString("occupation");
                    int membershipTypeId = resultSet.getInt("membership_type_id");
                    java.sql.Date expiryDate = resultSet.getDate("expiry_date");
                    member = new Member(id, fullname, dob, gender, contactNumber, email, address, country, postcode, occupation, membershipTypeId, expiryDate);
                }
            }
        } finally {
            disconnect();
        }

        return member;
    }

    public List<Member> getAllMembers() throws SQLException {
        List<Member> listMembers = new ArrayList<>();

        String sql = "SELECT * FROM members";

        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fullname = resultSet.getString("fullname");
                java.sql.Date dob = resultSet.getDate("dob");
                String gender = resultSet.getString("gender");
                String contactNumber = resultSet.getString("contact_number");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String country = resultSet.getString("country");
                String postcode = resultSet.getString("postcode");
                String occupation = resultSet.getString("occupation");
                int membershipTypeId = resultSet.getInt("membership_type_id");
                java.sql.Date expiryDate = resultSet.getDate("expiry_date");
                
                Member member = new Member(id, fullname, dob, gender, contactNumber, email, address, country, postcode, occupation, membershipTypeId, expiryDate);
                listMembers.add(member);
            }
        } finally {
            disconnect();
        }

        return listMembers;
    }

    public boolean updateMember(Member member) throws SQLException {
        String sql = "UPDATE members SET fullname = ?, dob = ?, gender = ?, contact_number = ?, email = ?, address = ?, country = ?, postcode = ?, occupation = ?, membership_type_id = ? WHERE id = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, member.getFullname());
            statement.setDate(2, member.getDob());
            statement.setString(3, member.getGender());
            statement.setString(4, member.getContactNumber());
            statement.setString(5, member.getEmail());
            statement.setString(6, member.getAddress());
            statement.setString(7, member.getCountry());
            statement.setString(8, member.getPostcode());
            statement.setString(9, member.getOccupation());
            statement.setInt(10, member.getMembershipTypeId());
            statement.setInt(11, member.getId());

            return statement.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }
/*
    public boolean deleteMember(int id) throws SQLException {
        connect();
        boolean rowDeleted = false;

        try {
            jdbcConnection.setAutoCommit(false); // Start transaction

            String sql = "DELETE FROM members WHERE id = ?";
            try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
                statement.setInt(1, id);
                rowDeleted = statement.executeUpdate() > 0;
            }

            jdbcConnection.commit(); // Commit transaction
        } catch (SQLException e) {
            jdbcConnection.rollback(); // Rollback transaction on error
            throw e;
        } finally {
            jdbcConnection.setAutoCommit(true); // Reset to default
            disconnect();
        }

        return rowDeleted;
    }*/
// Method to delete related records in the renew table
public void deleteRelatedRecords(int memberId) throws SQLException {
    String sql = "DELETE FROM renew WHERE member_id = ?";
    connect();

    try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
        statement.setInt(1, memberId);
        statement.executeUpdate();
    } finally {
        disconnect();
    }
}
public boolean deleteMember(int id) throws SQLException {
    deleteRelatedRecords(id); // Handle related records first
    
    String sql = "DELETE FROM members WHERE id = ?";
    connect();

    try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
        statement.setInt(1, id);
        return statement.executeUpdate() > 0;
    } finally {
        disconnect();
    }
}

    public boolean deleteRelatedMemberships(int memberId) throws SQLException {
        String sql = "DELETE FROM membership_types WHERE id = ?";
        connect();

        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            return statement.executeUpdate() > 0;
        } finally {
            disconnect();
        }
    }

    public void updateMembershipTypeAndExpiry(int memberId, int membershipTypeId, java.sql.Date newExpiryDate) throws SQLException {
        String sql = "UPDATE members SET membership_type_id = ?, expiry_date = ? WHERE id = ?";
        connect();
        try (PreparedStatement stmt = jdbcConnection.prepareStatement(sql)) {
            stmt.setInt(1, membershipTypeId);
            stmt.setDate(2, newExpiryDate);
            stmt.setInt(3, memberId);
            stmt.executeUpdate();
        } finally {
            disconnect();
        }
    }
}
