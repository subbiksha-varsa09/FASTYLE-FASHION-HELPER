
package databases;

import dao.WardrobeItemDAO;

public class TestSearchMaterial {
    public static void main(String[] args) {
        WardrobeItemDAO dao = new WardrobeItemDAO();

        System.out.println("Searching for Cotton items:");
        dao.searchByMaterial(2, "Cotton");
    }
}
