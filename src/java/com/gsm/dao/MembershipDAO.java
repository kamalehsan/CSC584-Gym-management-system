package com.gsm.dao;

import com.gsm.model.MembershipType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO {
    private String jdbcURL = "jdbc:derby://localhost:1527/GymManagementSystem";
    private String jdbcUsername = "app";
    private String jdbcPassword = "app";
    private Connection jdbcConnection;

    public MembershipDAO() {
        // Initialize your JDBC connection if needed
    }

    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public void insertMembershipType(String typeName, String description, double amount) throws SQLException {
        String sql = "INSERT INTO membership_types (type_name, description, amount) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, typeName);
        statement.setString(2, description);
        statement.setDouble(3, amount);

        statement.executeUpdate();

        statement.close();
        disconnect();
    }

    public List<MembershipType> getMembershipTypes() throws SQLException {
        List<MembershipType> listMembershipTypes = new ArrayList<>();

        String sql = "SELECT * FROM membership_types";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String type = resultSet.getString("type_name");
            String description = resultSet.getString("description");
            double amount = resultSet.getDouble("amount");

            MembershipType membershipType = new MembershipType(id, type, description, amount);
            listMembershipTypes.add(membershipType);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listMembershipTypes;
    }

    public MembershipType getMembershipTypeById(int id) throws SQLException {
        MembershipType membershipType = null;
        String sql = "SELECT * FROM membership_types WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String type = resultSet.getString("type_name");
            String description = resultSet.getString("description");
            double amount = resultSet.getDouble("amount");

            membershipType = new MembershipType(id, type, description, amount);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return membershipType;
    }
    public List<MembershipType> getAllMembershipTypes() throws SQLException {
        List<MembershipType> listMembershipType = new ArrayList<>();

        String sql = "SELECT * FROM membership_types";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String type = resultSet.getString("type_name");
            String description = resultSet.getString("description");
            double amount = resultSet.getDouble("amount");
            
            MembershipType membershipType = new MembershipType(id, type, description, amount);
            listMembershipType.add(membershipType);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return listMembershipType;
    }


    public boolean updateMembershipType(MembershipType membershipType) throws SQLException {
        String sql = "UPDATE membership_types SET type_name = ?, description = ?, amount = ? WHERE id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, membershipType.getType());
        statement.setString(2, membershipType.getDescription());
        statement.setDouble(3, membershipType.getAmount());
        statement.setInt(4, membershipType.getId());

        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowUpdated;
    }

    public boolean deleteMembershipType(int id) throws SQLException {
        String sql = "DELETE FROM membership_types WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return rowDeleted;
    }
}
