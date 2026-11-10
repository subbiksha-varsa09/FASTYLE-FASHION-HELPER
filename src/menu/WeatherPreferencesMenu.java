
package menu;

import dao.WeatherPreferencesDAO;
import java.util.Scanner;

public class WeatherPreferencesMenu {

    public static void run(Scanner sc, int userId) {

        WeatherPreferencesDAO dao = new WeatherPreferencesDAO();
        boolean running = true;

        while (running) {
            System.out.println("\n===== WEATHER PREFERENCES MENU =====");
            System.out.println("1. Save Weather Preferences");
            System.out.println("2. View My Weather Preferences");
            System.out.println("3. Delete a Weather Preference");
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
                    System.out.println(
                            "Weather: Hot, Cold, Rainy, Windy, All Weather");
                    System.out.print("Enter weather type: ");
                    String weather = sc.nextLine().trim();

                    if (!isValidWeather(weather)) {
                        System.out.println("Invalid weather type.");
                        break;
                    }

                    System.out.print("Preferred clothing (e.g. cotton): ");
                    String clothing = sc.nextLine().trim();

                    System.out.print("Preferred material (e.g. cotton): ");
                    String material = sc.nextLine().trim();

                    System.out.print(
                            "Comfort priority (Low, Medium, High): ");
                    String comfort = sc.nextLine().trim();

                    if (!isValidComfort(comfort)) {
                        System.out.println(
                                "Comfort priority must be Low, Medium, or High.");
                        break;
                    }

                    dao.savePreferences(
                            userId, weather, clothing, material, comfort);
                    break;

                case 2:
                    dao.displayPreferences(userId);
                    break;

                case 3:
                    dao.displayPreferences(userId);
                    System.out.print("Enter Preference ID to delete: ");

                    try {
                        int preferenceId =
                                Integer.parseInt(sc.nextLine().trim());

                        dao.deletePreference(userId, preferenceId);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Preference ID.");
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

    private static boolean isValidWeather(String weather) {
        return weather.equalsIgnoreCase("Hot")
                || weather.equalsIgnoreCase("Cold")
                || weather.equalsIgnoreCase("Rainy")
                || weather.equalsIgnoreCase("Windy")
                || weather.equalsIgnoreCase("All Weather");
    }

    private static boolean isValidComfort(String comfort) {
        return comfort.equalsIgnoreCase("Low")
                || comfort.equalsIgnoreCase("Medium")
                || comfort.equalsIgnoreCase("High");
    }
}
