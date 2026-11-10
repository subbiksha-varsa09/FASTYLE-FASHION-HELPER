
package databases;

import dao.OutfitHistoryDAO;

public class TestOutfitHistory {

    public static void main(String[] args) {

        OutfitHistoryDAO dao = new OutfitHistoryDAO();

        int userId = 2;
        int outfitId = 3;

        // Record that the outfit was generated
        System.out.println("Recording generated outfit...");
        dao.addHistory(userId, outfitId, "Generated");

        // Record that the outfit was selected
        System.out.println("\nRecording selected outfit...");
        dao.addHistory(userId, outfitId, "Selected");

        // Display outfit history
        dao.displayHistory(userId);
    }
}
