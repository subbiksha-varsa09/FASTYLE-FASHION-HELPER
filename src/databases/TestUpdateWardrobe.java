

package databases;

import dao.WardrobeItemDAO;

public class TestUpdateWardrobe {
    public static void main(String[] args) {
        WardrobeItemDAO dao = new WardrobeItemDAO();

        dao.updateItem(
            80, 2,
            "Black",      // color
            "Cotton",     // material
            "Casual",     // occasion
            "High",       // comfort level
            "All Weather" // weather
            );

        System.out.println("Blue Shirt occasion updated to Casual.");
    }
}

