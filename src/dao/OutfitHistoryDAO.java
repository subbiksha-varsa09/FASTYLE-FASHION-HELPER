
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import databases.DBConnection;

public class OutfitHistoryDAO {

    private String historyTable(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return "`user_" + userId + "_outfithistory`";
    }

    private String outfitsTable(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }
        return "`user_" + userId + "_outfits`";
    }

    // Record an action only if the outfit belongs to this user.
    public void addHistory(int userId, int outfitId, String actionType) {

        String sql = "INSERT INTO " + historyTable(userId)
                + " (user_id, outfit_id, action_type) "
                + "SELECT ?, ?, ? FROM " + outfitsTable(userId)
                + " WHERE outfit_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, outfitId);
            ps.setString(3, actionType);
            ps.setInt(4, outfitId);
            ps.setInt(5, userId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Outfit history recorded.");
            } else {
                System.out.println("Outfit not found for this user.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error recording history: " + e.getMessage());
        }
    }

    // Display the user's history.
    public void displayHistory(int userId) {

        String sql = "SELECT h.history_id, h.outfit_id, o.outfit_name, "
                + "o.occasion, o.weather_type, o.color_theme, "
                + "h.action_type, h.action_date "
                + "FROM " + historyTable(userId) + " h "
                + "JOIN " + outfitsTable(userId)
                + " o ON h.outfit_id = o.outfit_id "
                + "WHERE h.user_id = ? AND o.user_id = ? "
                + "ORDER BY h.action_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                System.out.println("\n===== MY OUTFIT HISTORY =====");

                while (rs.next()) {
                    found = true;

                    System.out.println(
                            "History ID: " + rs.getInt("history_id")
                            + " | Outfit ID: " + rs.getInt("outfit_id")
                            + " | Outfit: " + rs.getString("outfit_name")
                            + " | Occasion: " + rs.getString("occasion")
                            + " | Weather: " + rs.getString("weather_type")
                            + " | Style: " + rs.getString("color_theme")
                            + " | Action: " + rs.getString("action_type")
                            + " | Date: " + rs.getTimestamp("action_date"));
                }

                if (!found) {
                    System.out.println("No outfit history found.");
                }
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error displaying history: " + e.getMessage());
        }
    }

    // Update an existing history action.
    public void updateAction(int userId, int historyId, String actionType) {

        String sql = "UPDATE " + historyTable(userId)
                + " SET action_type = ? "
                + "WHERE history_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, actionType);
            ps.setInt(2, historyId);
            ps.setInt(3, userId);

            if (ps.executeUpdate() > 0) {
                System.out.println("History action updated successfully.");
            } else {
                System.out.println("History record not found.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error updating history: " + e.getMessage());
        }
    }

    // Delete a history record.
    public void deleteHistory(int userId, int historyId) {

        String sql = "DELETE FROM " + historyTable(userId)
                + " WHERE history_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, historyId);
            ps.setInt(2, userId);

            if (ps.executeUpdate() > 0) {
                System.out.println("History record deleted successfully.");
            } else {
                System.out.println("History record not found.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error deleting history: " + e.getMessage());
        }
    }
}
