
package menu;

import dao.UserDAO;
import java.io.Console;
import java.util.Scanner;

public class RegisterUser {

    public static void register(Scanner sc) {

        System.out.println("\n===== USER REGISTRATION =====");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

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

        if (password.length() < 8) {
            System.out.println(
                "Password must contain at least 8 characters."
            );
            return;
        }

        UserDAO userDAO = new UserDAO();

        int userId = userDAO.addUser(
            username, email, password
        );

        if (userId > 0) {
            System.out.println(
                "Registration successful! User ID: " + userId
            );
        } else {
            System.out.println(
                "Registration failed. Please try again."
            );
        }
    }
}
