
package databases;

import dao.WardrobeItemDAO;

public class TestSearchType {
    public static void main(String[] args) {
        WardrobeItemDAO dao = new WardrobeItemDAO();

        System.out.println("Searching for Shirt items:");
        dao.searchByType(2, "Shirt");
    }
}
