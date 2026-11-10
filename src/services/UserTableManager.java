
package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTableManager {

    private static final String[] TABLES = {
        "wardrobeitems",
        "favorites",
        "outfits",
        "outfititems",
        "outfithistory",
        "userpreferences",
        "weatherpreferences"
    };

    public static void createTables(Connection con, int userId)
            throws SQLException {

        if (userId <= 0) {
            throw new SQLException("Invalid user ID.");
        }

        try (Statement st = con.createStatement()) {

            for (String table : TABLES) {

                String newTable = "user_" + userId + "_" + table;
                String templateTable = "user_1_" + table;

                String sql = "CREATE TABLE IF NOT EXISTS `"
                        + newTable + "` LIKE `"
                        + templateTable + "`";

                st.executeUpdate(sql);

                System.out.println(
                    "Created/checked table: " + newTable
                );
            }
        }

        System.out.println(
            "All seven personal tables created successfully!"
        );
    }

    public static void dropTables(Connection con, int userId)
            throws SQLException {

        if (userId <= 0) {
            throw new SQLException("Invalid user ID.");
        }

        String[] dropOrder = {
            "outfithistory",
            "outfititems",
            "favorites",
            "outfits",
            "wardrobeitems",
            "userpreferences",
            "weatherpreferences"
        };

        try (Statement st = con.createStatement()) {

            for (String table : dropOrder) {

                String tableName = "user_" + userId + "_" + table;

                st.executeUpdate(
                    "DROP TABLE IF EXISTS `" + tableName + "`"
                );
            }
        }
    }
}
