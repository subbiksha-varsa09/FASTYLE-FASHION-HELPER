
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databases.DBConnection;

public class WeatherPreferencesDAO {

    private String tableName(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return "`user_" + userId + "_weatherpreferences`";
    }

    // Save or update a preference for a weather type.
    public void savePreferences(int userId, String weatherType,
                                String preferredClothing,
                                String preferredMaterial,
                                String comfortPriority) {

        String sql = "INSERT INTO " + tableName(userId)
                + " (user_id, weather_type, preferred_clothing, "
                + "preferred_material, comfort_priority) "
                + "VALUES (?, ?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE "
                + "preferred_clothing = VALUES(preferred_clothing), "
                + "preferred_material = VALUES(preferred_material), "
                + "comfort_priority = VALUES(comfort_priority)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, weatherType);
            ps.setString(3, preferredClothing);
            ps.setString(4, preferredMaterial);
            ps.setString(5, comfortPriority);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Weather preferences saved successfully.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error saving preferences: " + e.getMessage());
        }
    }

    // Display this user's weather preferences.
    public void displayPreferences(int userId) {

        String sql = "SELECT weather_preference_id, weather_type, "
                + "preferred_clothing, preferred_material, comfort_priority "
                + "FROM " + tableName(userId)
                + " WHERE user_id = ? ORDER BY weather_preference_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                System.out.println("\n===== MY WEATHER PREFERENCES =====");

                while (rs.next()) {
                    found = true;

                    System.out.println(
                            "Preference ID: "
                            + rs.getInt("weather_preference_id")
                            + " | Weather: " + rs.getString("weather_type")
                            + " | Clothing: "
                            + rs.getString("preferred_clothing")
                            + " | Material: "
                            + rs.getString("preferred_material")
                            + " | Comfort: "
                            + rs.getString("comfort_priority"));
                }

                if (!found) {
                    System.out.println("No weather preferences found.");
                }
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error displaying preferences: "
                    + e.getMessage());
        }
    }

    // Delete one of this user's preferences.
    public void deletePreference(int userId, int preferenceId) {

        String sql = "DELETE FROM " + tableName(userId)
                + " WHERE user_id = ? AND weather_preference_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, preferenceId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Weather preference deleted successfully.");
            } else {
                System.out.println("Preference not found.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error deleting preference: "
                    + e.getMessage());
        }
    }
}

