package com.gsm.dao;

import com.gsm.model.Renew;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RenewDAO {
    private String jdbcURL = "jdbc:derby://localhost:1527/GymManagementSystem";
    private String jdbcUsername = "app";
    private String jdbcPassword = "app";
    private Connection jdbcConnection;

    public RenewDAO() {
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

    public void insertRenew(Renew renew) throws SQLException {
        String sql = "INSERT INTO renew (member_id, total_amount) VALUES (?, ?)";
        connect();
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, renew.getMemberId());
            statement.setDouble(2, renew.getTotalAmount());

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } finally {
            disconnect();
        }
    }
}
