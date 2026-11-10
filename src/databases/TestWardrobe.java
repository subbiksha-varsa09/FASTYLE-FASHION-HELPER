
package databases;

import dao.WardrobeItemDAO;
import models.WardrobeItem;

public class TestWardrobe {

    public static void main(String[] args) {

        WardrobeItem item = new WardrobeItem(
            2,
            "Black T-Shirt",
            "Clothing",
            "T-Shirt",
            "Unisex",
            "Black",
            "Cotton",
            "Casual",
            "High",
            "All Weather"
        );

        WardrobeItemDAO dao = new WardrobeItemDAO();

        dao.addItem(item);

        System.out.println("\nMy Wardrobe Items:");
        dao.displayItems(2);
    }
}
