
package menu;

import databases.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import org.mindrot.jbcrypt.BCrypt;

public class LoginUser {

    public static int login(Scanner sc) {

        System.out.println("\n===== USER LOGIN =====");

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        String password;

        Console console = System.console();

        if (console != null) {
            password = new String(
                console.readPassword("Enter password: ")
            );
        } else {
            System.out.println(
                "Password hiding requires a real terminal."
            );
            System.out.print("Enter password: ");
            password = sc.nextLine();
        }

        String sql =
            "SELECT user_id, user_name, password_hash " +
            "FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String storedHash =
                        rs.getString("password_hash");

                    if (BCrypt.checkpw(password, storedHash)) {

                        int userId = rs.getInt("user_id");
                        String username =
                            rs.getString("user_name");

                        System.out.println(
                            "Login successful! Welcome, "
                            + username + "!"
                        );

                        return userId;
                    }
                }

                System.out.println(
                    "Invalid email or password."
                );

                return -1;
            }

        } catch (Exception e) {

            System.out.println(
                "Login error: " + e.getMessage()
            );

            return -1;

        }
    }
}
