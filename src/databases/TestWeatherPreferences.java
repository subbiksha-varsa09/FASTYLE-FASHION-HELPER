
package databases;

import dao.WeatherPreferencesDAO;

public class TestWeatherPreferences {

    public static void main(String[] args) {

        WeatherPreferencesDAO dao = new WeatherPreferencesDAO();

        int userId = 2;

        // Save preferences for hot weather
        System.out.println("Saving weather preferences...");

        dao.savePreferences(
                userId,
                "Hot",
                "T-Shirt",
                "Cotton",
                "High"
        );

        // Display saved preferences
        dao.displayPreferences(userId);
    }
}
