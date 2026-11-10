
package services;

import databases.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateOutfitHistoryTables {

    public static void updateAllTables() {

        String findTables =
            "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
            "WHERE TABLE_SCHEMA = DATABASE() " +
            "AND TABLE_NAME REGEXP '^user_[0-9]+_outfithistory$'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(findTables);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");

                String alterSQL =
                    "ALTER TABLE `" + tableName + "` " +
                    "MODIFY COLUMN action_type " +
                    "ENUM('Generated','Selected','Worn','SAVED','VIEWED') " +
                    "DEFAULT 'Generated'";

                try (Statement stmt = con.createStatement()) {
                    stmt.executeUpdate(alterSQL);
                    System.out.println(
                        "Updated successfully: " + tableName
                    );
                }
            }

            System.out.println("All outfit-history tables checked.");

        } catch (Exception e) {
            System.out.println(
                "Error updating tables: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        updateAllTables();
    }
}
