
package databases;

import java.util.Scanner;
import services.OutfitRecommendation;

public class TestRecommendation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your User ID: ");
        int userId = Integer.parseInt(sc.nextLine().trim());

        OutfitRecommendation recommendation =
                new OutfitRecommendation();

        recommendation.recommendOutfits(userId, sc);

        sc.close();
    }
}
