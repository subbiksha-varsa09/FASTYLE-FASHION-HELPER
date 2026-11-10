
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import services.UserTableManager;
import databases.DBConnection;

public class UserDAO {

    public void displayUsers() {

        String sql = "SELECT * FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("user_id") + " | " +
                    rs.getString("user_name") + " | " +
                    rs.getString("email")
                );
            }

        } catch (SQLException e) {
            System.out.println(
                "Error displaying users: " + e.getMessage()
            );
        }
    }

    public int addUser(String name, String email, String password) {

        String sql = "INSERT INTO users "
                + "(user_name, email, password_hash) "
                + "VALUES (?, ?, ?)";

        String hashedPassword = BCrypt.hashpw(
            password,
            BCrypt.gensalt()
        );

        int userId = -1;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashedPassword);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Registration failed.");
                return -1;
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (!keys.next()) {
                    throw new SQLException(
                        "Could not retrieve generated user ID."
                    );
                }

                userId = keys.getInt(1);
            }

            System.out.println(
                "User registered successfully! User ID: " + userId
            );

            // Create all seven personal tables.
            UserTableManager.createTables(con, userId);

            System.out.println(
                "FASTYLE personal tables created successfully!"
            );

            return userId;

        } catch (SQLException e) {

            System.out.println(
                "Registration or table creation failed: "
                + e.getMessage()
            );

            // Remove partially created tables and user record.
            if (userId > 0) {
                cleanupFailedRegistration(userId);
            }

            return -1;
        }
    }

    private void cleanupFailedRegistration(int userId) {

        try (Connection con = DBConnection.getConnection()) {

            UserTableManager.dropTables(con, userId);

            String sql = "DELETE FROM users WHERE user_id = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }

            System.out.println(
                "Incomplete registration cleaned up."
            );

        } catch (SQLException e) {

            System.out.println(
                "Cleanup failed: " + e.getMessage()
            );
        }
    }
}
