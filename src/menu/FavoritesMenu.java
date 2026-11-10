
package menu;

import java.util.Scanner;

import dao.FavoriteDAO;
import dao.WardrobeItemDAO;

public class FavoritesMenu {

    public static void run(Scanner sc, int userId) {

        FavoriteDAO dao = new FavoriteDAO();
        boolean running = true;

        while (running) {
            System.out.println("\n===== FAVORITES MENU =====");
            System.out.println("1. Add Wardrobe Item to Favorites");
            System.out.println("2. View My Favorites");
            System.out.println("3. Remove Favorite");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    new WardrobeItemDAO().displayItems(userId);
                    System.out.print("Enter the wardrobe Item ID to favorite: ");
                    try {
                        int itemId = Integer.parseInt(sc.nextLine().trim());
                        dao.addFavorite(userId, itemId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Item ID.");
                    }
                    break;

                case 2:
                    dao.displayFavorites(userId);
                    break;

                case 3:
                    dao.displayFavorites(userId);
                    System.out.print("Enter the Item ID to remove: ");
                    try {
                        int itemId = Integer.parseInt(sc.nextLine().trim());
                        dao.removeFavorite(userId, itemId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Item ID.");
                    }
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Choose an option from 1 to 4.");
            }
        }
    }
}
