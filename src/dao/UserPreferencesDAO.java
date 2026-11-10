
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databases.DBConnection;
import databases.UserPreferences;

public class UserPreferencesDAO {

    public void savePreferences(UserPreferences p) {

        String sql = "INSERT INTO userpreferences " +
            "(user_id, preferred_style, preferred_color, " +
            "preferred_fit, preferred_comfort, preferred_material) " +
            "VALUES (?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE " +
            "preferred_style = VALUES(preferred_style), " +
            "preferred_color = VALUES(preferred_color), " +
            "preferred_fit = VALUES(preferred_fit), " +
            "preferred_comfort = VALUES(preferred_comfort), " +
            "preferred_material = VALUES(preferred_material)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getUserId());
            ps.setString(2, p.getPreferredStyle());
            ps.setString(3, p.getPreferredColor());
            ps.setString(4, p.getPreferredFit());
            ps.setString(5, p.getPreferredComfort());
            ps.setString(6, p.getPreferredMaterial());

            ps.executeUpdate();
            System.out.println("Fashion preferences saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error saving preferences: " + e.getMessage());
        }
    }

    public void displayPreferences(int userId) {

        String sql = "SELECT * FROM userpreferences WHERE user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("User ID: " + rs.getInt("user_id"));
                    System.out.println("Style: " + rs.getString("preferred_style"));
                    System.out.println("Color: " + rs.getString("preferred_color"));
                    System.out.println("Fit: " + rs.getString("preferred_fit"));
                    System.out.println("Comfort: " + rs.getString("preferred_comfort"));
                    System.out.println("Material: " + rs.getString("preferred_material"));
                } else {
                    System.out.println("No preferences found for this user.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving preferences: " + e.getMessage());
        }
    }
}
