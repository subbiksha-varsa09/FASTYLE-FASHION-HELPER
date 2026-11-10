
package databases;

import dao.WardrobeItemDAO;

public class TestSearchOccasion {

    public static void main(String[] args) {

        WardrobeItemDAO dao = new WardrobeItemDAO();

        System.out.println("Searching for Party outfits:");

        dao.searchByOccasion(2, "Party");
    }
}
