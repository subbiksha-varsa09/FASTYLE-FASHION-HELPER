
package databases;

import dao.FavoriteDAO;

public class TestFavorites {

    public static void main(String[] args) {

        FavoriteDAO dao = new FavoriteDAO();

        int userId = 2;
        int itemId = 80;

        // Add the wardrobe item to favorites
        System.out.println("Adding item to favorites...");
        dao.addFavorite(userId, itemId);

        // Display favorite items
        System.out.println("\nDisplaying favorites...");
        dao.displayFavorites(userId);

        // Uncomment the next two lines if you want to remove the favorite
        // System.out.println("\nRemoving favorite...");
        // dao.removeFavorite(userId, itemId);
    }
}
