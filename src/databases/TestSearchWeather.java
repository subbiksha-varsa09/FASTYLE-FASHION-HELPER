
package databases;

import dao.WardrobeItemDAO;

public class TestSearchWeather {
    public static void main(String[] args) {
        WardrobeItemDAO dao = new WardrobeItemDAO();

        System.out.println("Searching for Hot weather clothes:");
        dao.searchByWeather(2, "Hot");
    }
}
