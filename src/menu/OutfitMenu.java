
package menu;

import java.util.Scanner;

import dao.OutfitDAO;
import models.Outfit;

public class OutfitMenu {

    public static void run(Scanner sc, int userId) {

        OutfitDAO dao = new OutfitDAO();
        int choice = 0;

        while (choice != 5) {

            System.out.println("\n===== MANAGE OUTFITS =====");
            System.out.println("1. Add Outfit");
            System.out.println("2. View My Outfits");
            System.out.println("3. Search by Occasion");
            System.out.println("4. Delete Outfit");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    addOutfit(sc, dao, userId);
                    break;

                case 2:
                    dao.displayOutfits(userId);
                    break;

                case 3:
                    System.out.print("Enter occasion: ");
                    String occasion = sc.nextLine().trim();
                    dao.searchByOccasion(userId, occasion);
                    break;

                case 4:
                    System.out.print("Enter outfit ID to delete: ");

                    try {
                        int outfitId =
                                Integer.parseInt(sc.nextLine().trim());
                        dao.deleteOutfit(outfitId, userId);
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid outfit ID.");
                    }
                    break;

                case 5:
                    System.out.println("Returning to main menu.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addOutfit(
            Scanner sc, OutfitDAO dao, int userId) {

        System.out.print("Enter outfit name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter occasion: ");
        String occasion = sc.nextLine().trim();

        System.out.print("Enter weather (Hot/Cold/Rainy/Windy/All Weather): ");
        String weather = sc.nextLine().trim();

        System.out.print("Enter style or color theme: ");
        String style = sc.nextLine().trim();

        if (name.isEmpty() || occasion.isEmpty()
                || weather.isEmpty() || style.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        Outfit outfit = new Outfit(
                userId, name, occasion, weather, style);

        dao.saveOutfit(outfit);
    }
}
