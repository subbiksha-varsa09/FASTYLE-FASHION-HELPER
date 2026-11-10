
package databases;

import dao.WardrobeItemDAO;

public class TestSearchColor {

    public static void main(String[] args) {

        WardrobeItemDAO dao = new WardrobeItemDAO();

        System.out.println("Searching for black items:");

        dao.searchByColor(2, "Black");
    }
}

