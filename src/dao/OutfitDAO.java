
package dao;

import databases.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Outfit;

public class OutfitDAO {

    private String tableName(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return "`user_" + userId + "_outfits`";
    }

    // 1. Save an outfit
    public void saveOutfit(Outfit outfit) {

        String sql = "INSERT INTO " + tableName(outfit.getUserId())
                + " (user_id, outfit_name, occasion, weather_type, color_theme)"
                + " VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, outfit.getUserId());
            ps.setString(2, outfit.getOutfitName());
            ps.setString(3, outfit.getOccasion());
            ps.setString(4, outfit.getWeatherType());
            ps.setString(5, outfit.getStyle());

            if (ps.executeUpdate() > 0) {
                System.out.println("Outfit saved successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error saving outfit: " + e.getMessage());
        }
    }

    // 2. Display outfits
    public void displayOutfits(int userId) {

        String sql = "SELECT * FROM " + tableName(userId);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                printOutfit(rs);
            }

            if (!found) {
                System.out.println("No outfits found.");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying outfits: " + e.getMessage());
        }
    }

    // 3. Search by occasion
    public void searchByOccasion(int userId, String occasion) {

        String sql = "SELECT * FROM " + tableName(userId)
                + " WHERE occasion = ?";

        search(sql, userId, occasion);
    }

    // 4. Search by weather
    public void searchByWeather(int userId, String weather) {

        String sql = "SELECT * FROM " + tableName(userId)
                + " WHERE weather_type = ?";

        search(sql, userId, weather);
    }

    private void search(String sql, int userId, String value) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, value);

            boolean found = false;

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    found = true;
                    printOutfit(rs);
                }
            }

            if (!found) {
                System.out.println("No matching outfits found.");
            }

        } catch (SQLException e) {
            System.out.println("Error searching outfits: " + e.getMessage());
        }
    }

    // 5. Delete an outfit
    public void deleteOutfit(int outfitId, int userId) {

        String sql = "DELETE FROM " + tableName(userId)
                + " WHERE outfit_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, outfitId);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Outfit deleted successfully!");
            } else {
                System.out.println("Outfit not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting outfit: " + e.getMessage());
        }
    }

    private void printOutfit(ResultSet rs) throws SQLException {

        System.out.println(
                "Outfit ID: " + rs.getInt("outfit_id")
                + " | Name: " + rs.getString("outfit_name")
                + " | Occasion: " + rs.getString("occasion")
                + " | Weather: " + rs.getString("weather_type")
                + " | Style: " + rs.getString("color_theme")
        );
    }
}
