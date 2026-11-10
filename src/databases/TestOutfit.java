
package databases;

import dao.OutfitDAO;
import models.Outfit;

public class TestOutfit {
    public static void main(String[] args) {

        OutfitDAO dao = new OutfitDAO();

        Outfit outfit = new Outfit(
            2,
            "Casual Black Outfit",
            "Casual",
            "Hot",
            "Black"
        );

        System.out.println("Saving outfit...");
        dao.saveOutfit(outfit);

        System.out.println("\nSaved outfits for user 2:");
        dao.displayOutfits(2);
    }
}
