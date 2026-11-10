
package dao;

import databases.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.WardrobeItem;

public class WardrobeItemDAO {

    // Generate the wardrobe table name for a specific user.
    private String tableName(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return "`user_" + userId + "_wardrobeitems`";
    }

    // 1. Add wardrobe item
    public void addItem(WardrobeItem item) {
        String table = tableName(item.getUserId());
        String sql = "INSERT INTO " + table
        + " (user_id, item_name, item_category, item_type, "
        + "suitable_for, color, material, occasion, "
        + "comfort_level, weather_type) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, item.getUserId());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getItemCategory());
            ps.setString(4, item.getItemType());
            ps.setString(5, item.getSuitableFor());
            ps.setString(6, item.getColor());
            ps.setString(7, item.getMaterial());
            ps.setString(8, item.getOccasion());
            ps.setString(9, item.getComfortLevel());
            ps.setString(10, item.getWeatherType());
            if (ps.executeUpdate() > 0) {
                System.out.println("Wardrobe item added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }


    // 2. Display wardrobe items
    public void displayItems(int userId) {
        String sql = "SELECT * FROM " + tableName(userId);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "Item ID: " + rs.getInt("item_id")
                    + " | Name: " + rs.getString("item_name")
                    + " | Category: " + rs.getString("item_category")
                    + " | Type: " + rs.getString("item_type")
                    + " | Color: " + rs.getString("color")
                    + " | Material: " + rs.getString("material")
                    + " | Occasion: " + rs.getString("occasion")
                );
            }

            if (!found) {
                System.out.println("No wardrobe items found.");
            }

        } catch (SQLException e) {
            System.out.println("Error displaying items: " + e.getMessage());
        }
    }

    // 3. Delete wardrobe item
    public void deleteItem(int itemId, int userId) {
        String sql = "DELETE FROM " + tableName(userId)
                   + " WHERE item_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Wardrobe item deleted successfully.");
            } else {
                System.out.println("Item not found for this user.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting item: " + e.getMessage());
        }
    }

    // 4. Update wardrobe item
    public void updateItem(int itemId, int userId,
                           String color, String material,
                           String occasion, String comfortLevel,
                           String weatherType) {

        String sql = "UPDATE " + tableName(userId)
                + " SET color = ?, material = ?, occasion = ?, "
                + "comfort_level = ?, weather_type = ? WHERE item_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, color);
            ps.setString(2, material);
            ps.setString(3, occasion);
            ps.setString(4, comfortLevel);
            ps.setString(5, weatherType);
            ps.setInt(6, itemId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Wardrobe item updated successfully.");
            } else {
                System.out.println("Item not found for this user.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating item: " + e.getMessage());
        }
    }

    // 5. Search by category
    public void searchByCategory(int userId, String category) {
        search(userId, "item_category", category,
               "No items found for this category.");
    }

    // 6. Search by color
    public void searchByColor(int userId, String color) {
        search(userId, "color", color,
               "No items found for this color.");
    }

    // 7. Search by occasion
    public void searchByOccasion(int userId, String occasion) {
        search(userId, "occasion", occasion,
               "No items found for this occasion.");
    }

    // 8. Search by weather, including All Weather
    public void searchByWeather(int userId, String weather) {
        String sql = "SELECT * FROM " + tableName(userId)
                + " WHERE weather_type = ? OR weather_type = 'All Weather'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, weather);
            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(
                        "Item ID: " + rs.getInt("item_id")
                        + " | Name: " + rs.getString("item_name")
                        + " | Category: " + rs.getString("item_category")
                        + " | Color: " + rs.getString("color")
                        + " | Weather: " + rs.getString("weather_type")
                    );
                }

                if (!found) {
                    System.out.println("No items found for this weather.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching by weather: " + e.getMessage());
        }
    }

    // 9. Search by clothing type
    public void searchByType(int userId, String itemType) {
        search(userId, "item_type", itemType,
               "No items found for this type.");
    }

    // 10. Search by material
    public void searchByMaterial(int userId, String material) {
        search(userId, "material", material,
               "No items found for this material.");
    }

    // Shared search helper. Column names are internal constants only.
    private void search(int userId, String column, String value,
                        String emptyMessage) {

        if (!column.matches("item_category|color|occasion|item_type|material")) {
            throw new IllegalArgumentException("Invalid search column.");
        }

        String sql = "SELECT * FROM " + tableName(userId)
                   + " WHERE " + column + " = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, value);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    printBasicItem(rs);
                }

                if (!found) {
                    System.out.println(emptyMessage);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching wardrobe: " + e.getMessage());
        }
    }

    private void printBasicItem(ResultSet rs) throws SQLException {
        System.out.println(
            "Item ID: " + rs.getInt("item_id")
            + " | Name: " + rs.getString("item_name")
            + " | Category: " + rs.getString("item_category")
            + " | Color: " + rs.getString("color")
            + " | Occasion: " + rs.getString("occasion")
        );
    }
    
    // Retrieve all wardrobe items as Java objects for recommendations.
    public java.util.List<WardrobeItem> getItemsForRecommendation(int userId) {

        java.util.List<WardrobeItem> items = new java.util.ArrayList<>();

        String sql = "SELECT * FROM " + tableName(userId);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WardrobeItem item = new WardrobeItem(
                    userId,
                    rs.getString("item_name"),
                    rs.getString("item_category"),
                    rs.getString("item_type"),
                    rs.getString("suitable_for"),
                    rs.getString("color"),
                    rs.getString("material"),
                    rs.getString("occasion"),
                    rs.getString("comfort_level"),
                    rs.getString("weather_type")
                );

                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println(
                "Error retrieving wardrobe for recommendations: "
                + e.getMessage()
            );
        }

        return items;
    }

}
