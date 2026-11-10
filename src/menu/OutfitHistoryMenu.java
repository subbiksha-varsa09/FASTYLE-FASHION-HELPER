
package menu;

import java.util.Scanner;

import dao.OutfitDAO;
import dao.OutfitHistoryDAO;

public class OutfitHistoryMenu {

    public static void run(Scanner sc, int userId) {

        OutfitHistoryDAO dao = new OutfitHistoryDAO();
        boolean running = true;

        while (running) {
            System.out.println("\n===== OUTFIT HISTORY MENU =====");
            System.out.println("1. Record an Outfit Action");
            System.out.println("2. View My Outfit History");
            System.out.println("3. Update a History Action");
            System.out.println("4. Delete a History Record");
            System.out.println("5. Back to Main Menu");
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
                    new OutfitDAO().displayOutfits(userId);

                    System.out.print("Enter Outfit ID: ");
                    try {
                        int outfitId = Integer.parseInt(sc.nextLine().trim());

                        System.out.print(
                                "Enter action (VIEWED, WORN, SAVED): ");
                        String action = sc.nextLine().trim().toUpperCase();

                        if (!isValidAction(action)) {
                            System.out.println(
                                    "Use VIEWED, WORN, or SAVED.");
                            break;
                        }

                        dao.addHistory(userId, outfitId, action);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Outfit ID.");
                    }
                    break;

                case 2:
                    dao.displayHistory(userId);
                    break;

                case 3:
                    dao.displayHistory(userId);

                    System.out.print("Enter History ID to update: ");
                    try {
                        int historyId =
                                Integer.parseInt(sc.nextLine().trim());

                        System.out.print(
                                "Enter new action (VIEWED, WORN, SAVED): ");
                        String action =
                                sc.nextLine().trim().toUpperCase();

                        if (!isValidAction(action)) {
                            System.out.println(
                                    "Use VIEWED, WORN, or SAVED.");
                            break;
                        }

                        dao.updateAction(userId, historyId, action);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid History ID.");
                    }
                    break;

                case 4:
                    dao.displayHistory(userId);

                    System.out.print("Enter History ID to delete: ");
                    try {
                        int historyId =
                                Integer.parseInt(sc.nextLine().trim());
                        dao.deleteHistory(userId, historyId);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid History ID.");
                    }
                    break;

                case 5:
                    running = false;
                    break;

                default:
                    System.out.println("Choose an option from 1 to 5.");
            }
        }
    }

    private static boolean isValidAction(String action) {
        return action.equals("VIEWED")
                || action.equals("WORN")
                || action.equals("SAVED");
    }
}
