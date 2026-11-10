
package menu;

import databases.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.Console;
import org.mindrot.jbcrypt.BCrypt;

public class ResetPassword {

    public static void reset(Scanner sc) {

        System.out.println("\n===== RESET PASSWORD =====");

        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        String newPassword;

        Console console = System.console();

        if (console != null) {
            newPassword = new String(
                console.readPassword("Enter new password: ")
            );
        } else {
            System.out.println(
                "Password hiding requires a real terminal."
            );
            System.out.print("Enter new password: ");
            newPassword = sc.nextLine();
        }

        if (newPassword.length() < 8) {
            System.out.println(
                "Password must contain at least 8 characters."
            );
            return;
        }

        String checkSQL =
            "SELECT user_id FROM users WHERE email = ?";

        String updateSQL =
            "UPDATE users SET password_hash = ? WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement checkPS =
                 con.prepareStatement(checkSQL)) {

            checkPS.setString(1, email);

            try (ResultSet rs = checkPS.executeQuery()) {

                if (!rs.next()) {
                    System.out.println("Email not found.");
                    return;
                }
            }

            String hashedPassword = BCrypt.hashpw(
                newPassword,
                BCrypt.gensalt()
            );

            try (PreparedStatement updatePS =
                     con.prepareStatement(updateSQL)) {

                updatePS.setString(1, hashedPassword);
                updatePS.setString(2, email);

                int rowsUpdated = updatePS.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println(
                        "Password reset successfully!"
                    );
                } else {
                    System.out.println(
                        "Password reset failed."
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                "Error resetting password: " + e.getMessage()
            );
        }
    }
}
