
package menu;

import dao.WardrobeItemDAO;
import java.util.Scanner;
import models.WardrobeItem;

public class WardrobeMenu {

    private static final WardrobeItemDAO dao = new WardrobeItemDAO();

    public static void run(Scanner sc, int userId) {

        int choice = 0;

        while (choice != 6) {

            System.out.println("\n===== MANAGE WARDROBE =====");
            System.out.println("1. Add Clothing Item");
            System.out.println("2. View My Wardrobe");
            System.out.println("3. Search Clothing");
            System.out.println("4. Update Clothing Item");
            System.out.println("5. Delete Clothing Item");
            System.out.println("6. Back to FASTYLE Menu");
            System.out.print("Enter choice: ");

            choice = readInt(sc);

            switch (choice) {

                case 1:
                    addItem(sc, userId);
                    break;

                case 2:
                    dao.displayItems(userId);
                    break;

                case 3:
                    searchMenu(sc, userId);
                    break;

                case 4:
                    updateItem(sc, userId);
                    break;

                case 5:
                    System.out.print("Enter item ID to delete: ");
                    int deleteId = readInt(sc);
                    dao.deleteItem(deleteId, userId);
                    break;

                case 6:
                    System.out.println("Returning to FASTYLE Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addItem(Scanner sc, int userId) {

        System.out.print("Item name: ");
        String name = sc.nextLine().trim();

        System.out.println("Category: Clothing, Footwear, Accessory, Bag");
        System.out.print("Category: ");
        String category = sc.nextLine().trim();

        System.out.print("Item type (e.g. Shirt, Jeans, Shoes): ");
        String type = sc.nextLine().trim();

        System.out.print("Suitable for (e.g. Men, Women, Unisex): ");
        String suitableFor = sc.nextLine().trim();

        System.out.print("Color: ");
        String color = sc.nextLine().trim();

        System.out.print("Material: ");
        String material = sc.nextLine().trim();

        System.out.print("Occasion (e.g. Casual, Party, Formal): ");
        String occasion = sc.nextLine().trim();

        System.out.println("Comfort: Low, Medium, High");
        System.out.print("Comfort level: ");
        String comfort = sc.nextLine().trim();

        System.out.println("Weather: Hot, Cold, Rainy, Windy, All Weather");
        System.out.print("Weather type: ");
        String weather = sc.nextLine().trim();

        if (name.isEmpty() || category.isEmpty() || type.isEmpty()
                || suitableFor.isEmpty() || color.isEmpty()
                || material.isEmpty() || occasion.isEmpty()
                || comfort.isEmpty() || weather.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        WardrobeItem item = new WardrobeItem(
            userId, name, category, type, suitableFor,
            color, material, occasion, comfort, weather
        );

        dao.addItem(item);
    }

    private static void searchMenu(Scanner sc, int userId) {

        System.out.println("\n--- SEARCH WARDROBE ---");
        System.out.println("1. By Category");
        System.out.println("2. By Color");
        System.out.println("3. By Occasion");
        System.out.println("4. By Weather");
        System.out.println("5. By Type");
        System.out.println("6. By Material");
        System.out.print("Enter choice: ");

        int choice = readInt(sc);

        System.out.print("Enter search value: ");
        String value = sc.nextLine().trim();

        switch (choice) {
            case 1: dao.searchByCategory(userId, value); break;
            case 2: dao.searchByColor(userId, value); break;
            case 3: dao.searchByOccasion(userId, value); break;
            case 4: dao.searchByWeather(userId, value); break;
            case 5: dao.searchByType(userId, value); break;
            case 6: dao.searchByMaterial(userId, value); break;
            default: System.out.println("Invalid search choice.");
        }
    }

    private static void updateItem(Scanner sc, int userId) {

        System.out.print("Enter item ID to update: ");
        int itemId = readInt(sc);

        System.out.print("New color: ");
        String color = sc.nextLine().trim();

        System.out.print("New material: ");
        String material = sc.nextLine().trim();

        System.out.print("New occasion: ");
        String occasion = sc.nextLine().trim();

        System.out.print("New comfort level (Low/Medium/High): ");
        String comfort = sc.nextLine().trim();

        System.out.print("New weather type (Hot/Cold/Rainy/Windy/All Weather): ");
        String weather = sc.nextLine().trim();

        dao.updateItem(
            itemId, userId, color, material, occasion, comfort, weather
        );
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String value = sc.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
