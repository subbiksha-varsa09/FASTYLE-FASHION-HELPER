package menu;

import java.util.Scanner;
import services.OutfitRecommendation;


public class FastyleMenu {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {

            int activeUserId = -1;
            boolean running = true;

            while (running) {

                System.out.println("\n========== FASTYLE ==========");
                System.out.println("Fashion Helper Website");

                if (activeUserId > 0) {
                    System.out.println("Logged-in User ID: " + activeUserId);
                } else {
                    System.out.println("Status: Not logged in");
                }

                System.out.println("\n1. Register");
                System.out.println("2. Login");
                System.out.println("3. Manage Wardrobe");
                System.out.println("4. Manage Outfits");
                System.out.println("5. Outfit Recommendations");
                System.out.println("6. Favorites");
                System.out.println("7. Outfit History");
                System.out.println("8. Weather Preferences");
                System.out.println("9. Forgot / Reset Password");
                System.out.println("10. Logout");
                System.out.println("11. Exit");
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
                        RegisterUser.register(sc);
                        break;

                    case 2: {
                        int loggedInUserId = LoginUser.login(sc);

                        if (loggedInUserId > 0) {
                            activeUserId = loggedInUserId;
                        }
                        break;
                    }

                    case 3:
                        if (requireLogin(activeUserId)) {
                            WardrobeMenu.run(sc, activeUserId);
                        }
                        break;

                    case 4:
                        if (requireLogin(activeUserId)) {
                            OutfitMenu.run(sc, activeUserId);
                        }
                        break;

                    case 5:
                        if (requireLogin(activeUserId)) {
                            new OutfitRecommendation()
                                    .recommendOutfits(activeUserId, sc);
                        }
                        break;

                    case 6:
                        if (requireLogin(activeUserId)) {
                            FavoritesMenu.run(sc, activeUserId);
                        }
                        break;

                    case 7:
                        if (requireLogin(activeUserId)) {
                            OutfitHistoryMenu.run(sc, activeUserId);
                        }
                        break;

                    case 8:
                        if (requireLogin(activeUserId)) {
                            WeatherPreferencesMenu.run(sc, activeUserId);
                        }
                        break;

                    case 9:
                        ResetPassword.reset(sc);
                        break;

                    case 10:
                        if (activeUserId > 0) {
                            activeUserId = -1;
                            System.out.println("Logged out successfully.");
                        } else {
                            System.out.println("You are not logged in.");
                        }
                        break;

                    case 11:
                        running = false;
                        System.out.println("Thank you for using FASTYLE!");
                        break;

                    default:
                        System.out.println("Choose an option from 1 to 11.");
                }
            }
        }
    }

    private static boolean requireLogin(int userId) {
        if (userId <= 0) {
            System.out.println("Please log in first.");
            return false;
        }
        return true;
    }
}
