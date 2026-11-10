
package dao;

import databases.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteDAO {

    private String favoritesTable(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return "`user_" + userId + "_favorites`";
    }

    private String wardrobeTable(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return "`user_" + userId + "_wardrobeitems`";
    }

    public void addFavorite(int userId, int itemId) {

        String favorites = favoritesTable(userId);
        String wardrobe = wardrobeTable(userId);

        String checkSql = "SELECT 1 FROM " + favorites
                + " WHERE user_id = ? AND item_id = ?";

        String itemSql = "SELECT 1 FROM " + wardrobe
                + " WHERE user_id = ? AND item_id = ?";

        String insertSql = "INSERT INTO " + favorites
                + " (user_id, item_id) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement(itemSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, itemId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println(
                                "Wardrobe item not found for this user.");
                        return;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, itemId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(
                                "Item is already in favorites.");
                        return;
                    }
                }
            }

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, itemId);

                if (ps.executeUpdate() > 0) {
                    System.out.println(
                            "Item added to favorites successfully.");
                }
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error adding favorite: " + e.getMessage());
        }
    }

    public void displayFavorites(int userId) {

        String sql = "SELECT DISTINCT w.item_id, w.item_name, "
                + "w.item_category, w.color "
                + "FROM " + favoritesTable(userId) + " f "
                + "JOIN " + wardrobeTable(userId)
                + " w ON f.item_id = w.item_id "
                + "WHERE f.user_id = ? AND w.user_id = ? "
                + "ORDER BY w.item_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                System.out.println("\n===== MY FAVORITES =====");

                while (rs.next()) {
                    found = true;
                    System.out.println(
                            "Item ID: " + rs.getInt("item_id")
                            + " | Name: " + rs.getString("item_name")
                            + " | Category: "
                            + rs.getString("item_category")
                            + " | Color: " + rs.getString("color"));
                }

                if (!found) {
                    System.out.println("No favorite items found.");
                }
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println(
                    "Error displaying favorites: " + e.getMessage());
        }
    }

    public void removeFavorite(int userId, int itemId) {

        String sql = "DELETE FROM " + favoritesTable(userId)
                + " WHERE user_id = ? AND item_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, itemId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Favorite removed successfully.");
            } else {
                System.out.println("Favorite not found.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error removing favorite: " + e.getMessage());
        }
    }
}
