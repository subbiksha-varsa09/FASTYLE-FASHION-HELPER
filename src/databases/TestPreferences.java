
package databases;

import dao.UserPreferencesDAO;

public class TestPreferences {

    public static void main(String[] args) {

        UserPreferences preferences = new UserPreferences(
            2,
            "Casual",
            "Black",
            "Regular",
            "High",
            "Cotton"
        );

        UserPreferencesDAO dao = new UserPreferencesDAO();

        dao.savePreferences(preferences);

        System.out.println("\nSaved Fashion Preferences:");
        dao.displayPreferences(2);
    }
}
