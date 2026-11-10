
package databases;

import dao.WardrobeItemDAO;

public class TestDeleteWardrobe {

    public static void main(String[] args) {

        WardrobeItemDAO dao = new WardrobeItemDAO();

        System.out.println("Before deletion:");
        dao.displayItems(2);

        System.out.println("\nDeleting item ID 79...");
        dao.deleteItem(79, 2);

        System.out.println("\nAfter deletion:");
        dao.displayItems(2);
    }
}
