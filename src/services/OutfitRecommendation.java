package services;

import dao.WardrobeItemDAO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import models.WardrobeItem;

public class OutfitRecommendation {

    private final WardrobeItemDAO wardrobeDAO = new WardrobeItemDAO();

    private static class OutfitChoice {
        WardrobeItem top, bottom, dress, footwear, bag;
        WardrobeItem earrings, necklace, bracelet, ring;
        WardrobeItem hairAccessory, otherAccessory;
        int score;

        OutfitChoice(WardrobeItem top, WardrobeItem bottom,
                WardrobeItem dress, WardrobeItem footwear,
                WardrobeItem bag, WardrobeItem earrings,
                WardrobeItem necklace, WardrobeItem bracelet,
                WardrobeItem ring, WardrobeItem hairAccessory,
                WardrobeItem otherAccessory, int score) {

            this.top = top;
            this.bottom = bottom;
            this.dress = dress;
            this.footwear = footwear;
            this.bag = bag;
            this.earrings = earrings;
            this.necklace = necklace;
            this.bracelet = bracelet;
            this.ring = ring;
            this.hairAccessory = hairAccessory;
            this.otherAccessory = otherAccessory;
            this.score = score;
        }
    }

    public void recommendOutfits(int userId, Scanner sc) {

        List<WardrobeItem> items =
                wardrobeDAO.getItemsForRecommendation(userId);

        if (items == null || items.isEmpty()) {
            System.out.println("Your wardrobe is empty. Add items first.");
            return;
        }

        System.out.println("\n========== FASTYLE SMART STYLIST ==========");
        System.out.println("Items found in your wardrobe: " + items.size());

        System.out.print("Enter occasion (Party, Casual, College, Formal): ");
        String occasion = sc.nextLine().trim();

        System.out.print("Enter weather (Hot, Cold, Rainy, Windy, All Weather): ");
        String weather = sc.nextLine().trim();

        if (occasion.isEmpty() || weather.isEmpty()) {
            System.out.println("Occasion and weather are required.");
            return;
        }

        List<WardrobeItem> tops = new ArrayList<>();
        List<WardrobeItem> bottoms = new ArrayList<>();
        List<WardrobeItem> dresses = new ArrayList<>();
        List<WardrobeItem> footwear = new ArrayList<>();
        List<WardrobeItem> bags = new ArrayList<>();
        List<WardrobeItem> accessories = new ArrayList<>();

        // Filter wardrobe items by occasion and weather.
        for (WardrobeItem item : items) {

            if (occasionScore(item, occasion) == 0) {
                continue;
            }

            if (!weatherMatches(item.getWeatherType(), weather)) {
                continue;
            }

            String category = lower(item.getItemCategory());
            String type = lower(item.getItemType());

            if (isFootwear(category, type)) {
                footwear.add(item);
            } else if (isBag(category, type)) {
                bags.add(item);
            } else if (category.equals("accessory")) {
                accessories.add(item);
            } else if (isDress(type)) {
                dresses.add(item);
            } else if (isBottom(type)) {
                bottoms.add(item);
            } else if (isTop(type)) {
                tops.add(item);
            }
        }

        boolean hasDressOutfit =
                !dresses.isEmpty() && !footwear.isEmpty();

        boolean hasSeparateOutfit =
                !tops.isEmpty() && !bottoms.isEmpty()
                && !footwear.isEmpty();

        if (!hasDressOutfit && !hasSeparateOutfit) {

            System.out.println(
                    "\nNot enough suitable items for a complete "
                    + occasion + " outfit.");

            if (dresses.isEmpty() && tops.isEmpty()) {
                System.out.println("Missing: a suitable top or dress.");
            }

            if (dresses.isEmpty() && bottoms.isEmpty()) {
                System.out.println("Missing: suitable bottoms.");
            }

            if (footwear.isEmpty()) {
                System.out.println("Missing: suitable footwear.");
            }

            System.out.println(
                    "Add suitable items for " + occasion
                    + " and " + weather + " weather, then try again.");
            return;
        }

        List<OutfitChoice> choices = new ArrayList<>();

        // Generate dress-based outfits.
        for (WardrobeItem dress : dresses) {
            for (WardrobeItem shoes : footwear) {
                choices.add(createChoice(
                        null, null, dress, shoes,
                        bags, accessories, occasion, weather));
            }
        }

        // Generate top and bottom outfits.
        for (WardrobeItem top : tops) {
            for (WardrobeItem bottom : bottoms) {
                for (WardrobeItem shoes : footwear) {
                    choices.add(createChoice(
                            top, bottom, null, shoes,
                            bags, accessories, occasion, weather));
                }
            }
        }

        // Sort all combinations from highest score to lowest.
        choices.sort(
                Comparator.comparingInt((OutfitChoice c) -> c.score)
                        .reversed()
        );

        System.out.println(
                "\n========== YOUR " + occasion.toUpperCase()
                + " OUTFIT OPTIONS ==========");

        // IMPORTANT: Display only the best 5 choices.
        int limit = Math.min(5, choices.size());

        for (int i = 0; i < limit; i++) {

            OutfitChoice choice = choices.get(i);

            System.out.println("\nOPTION " + (i + 1));

            if (choice.dress != null) {
                System.out.println("Dress: " + choice.dress.getItemName());
            } else {
                System.out.println("Top: " + choice.top.getItemName());
                System.out.println("Bottom: " + choice.bottom.getItemName());
            }

            System.out.println(
                    "Footwear: " + choice.footwear.getItemName());

            printItem("Earrings", choice.earrings);
            printItem("Necklace / Chain", choice.necklace);
            printItem("Bracelet / Bangles", choice.bracelet);
            printItem("Ring", choice.ring);
            printItem("Hair Accessory", choice.hairAccessory);
            printItem("Other Accessory", choice.otherAccessory);
            printItem("Bag", choice.bag);

            System.out.println("Style score: " + choice.score);
        }

        System.out.println(
                "\nTotal outfit combinations found: " + choices.size());

        System.out.println(
                "Showing the best " + limit
                + " outfit options for " + occasion + ".");
    }

    private OutfitChoice createChoice(
            WardrobeItem top,
            WardrobeItem bottom,
            WardrobeItem dress,
            WardrobeItem shoes,
            List<WardrobeItem> bags,
            List<WardrobeItem> accessories,
            String occasion,
            String weather) {

        List<WardrobeItem> base = new ArrayList<>();

        if (top != null) base.add(top);
        if (bottom != null) base.add(bottom);
        if (dress != null) base.add(dress);

        base.add(shoes);

        WardrobeItem bag =
                bestMatch(bags, base, occasion, weather);

        WardrobeItem earrings =
                bestAccessory(accessories, "earring", base, occasion, weather);

        WardrobeItem necklace =
                bestAccessory(accessories, "necklace", base, occasion, weather);

        if (necklace == null) {
            necklace = bestAccessory(
                    accessories, "chain", base, occasion, weather);
        }

        if (necklace == null) {
            necklace = bestAccessory(
                    accessories, "choker", base, occasion, weather);
        }

        WardrobeItem bracelet =
                bestAccessory(accessories, "bracelet", base, occasion, weather);

        if (bracelet == null) {
            bracelet = bestAccessory(
                    accessories, "bangles", base, occasion, weather);
        }

        WardrobeItem ring =
                bestAccessory(accessories, "ring", base, occasion, weather);

        WardrobeItem hair =
                bestAccessory(accessories, "hair", base, occasion, weather);

        WardrobeItem other =
                bestOtherAccessory(accessories, base, occasion, weather);

        int score = 0;

        for (WardrobeItem item : base) {
            score += scoreItem(item, occasion, weather);
        }

        WardrobeItem[] selected = {
            bag, earrings, necklace, bracelet, ring, hair, other
        };

        for (WardrobeItem item : selected) {
            if (item != null) {
                score += scoreItem(item, occasion, weather);

                for (WardrobeItem baseItem : base) {
                    score += colorScore(baseItem, item);
                }
            }
        }

        if (bag != null) {
            for (WardrobeItem accessory :
                    new WardrobeItem[]{earrings, necklace, bracelet,
                        ring, hair, other}) {
                if (accessory != null) {
                    score += colorScore(bag, accessory);
                }
            }
        }

        return new OutfitChoice(
                top, bottom, dress, shoes, bag,
                earrings, necklace, bracelet, ring,
                hair, other, score);
    }

    private WardrobeItem bestAccessory(
            List<WardrobeItem> items,
            String requestedType,
            List<WardrobeItem> base,
            String occasion,
            String weather) {

        WardrobeItem best = null;
        int bestScore = Integer.MIN_VALUE;

        for (WardrobeItem item : items) {

            String type = lower(item.getItemType());
            String name = lower(item.getItemName());

            boolean matches = false;

            switch (requestedType) {

                case "earring":
                    matches = type.contains("earring")
                            || name.contains("earring")
                            || type.contains("jhumka")
                            || name.contains("jhumka");
                    break;

                case "necklace":
                    matches = type.contains("necklace")
                            || name.contains("necklace");
                    break;

                case "chain":
                    matches = type.equals("chain")
                            || name.contains("chain");
                    break;

                case "choker":
                    matches = type.contains("choker")
                            || name.contains("choker");
                    break;

                case "bracelet":
                    matches = type.contains("bracelet")
                            || name.contains("bracelet");
                    break;

                case "bangles":
                    matches = type.contains("bangle")
                            || name.contains("bangle");
                    break;

                case "ring":
                    matches = type.equals("ring")
                            || type.equals("finger ring")
                            || name.contains("finger ring")
                            || name.endsWith(" ring");
                    break;

                case "hair":
                    matches = containsAny(type,
                                "hair", "scrunchie", "headband")
                            || containsAny(name,
                                "hair", "scrunchie", "headband");
                    break;
            }

            if (!matches) {
                continue;
            }

            int score = accessoryScore(
                    item, base, occasion, weather);

            if (score > bestScore) {
                bestScore = score;
                best = item;
            }
        }

        return best;
    }

    private WardrobeItem bestOtherAccessory(
            List<WardrobeItem> items,
            List<WardrobeItem> base,
            String occasion,
            String weather) {

        WardrobeItem best = null;
        int bestScore = Integer.MIN_VALUE;

        for (WardrobeItem item : items) {

            String type = lower(item.getItemType());
            String name = lower(item.getItemName());

            if (containsAny(type, "earring", "jhumka", "necklace",
                    "chain", "choker", "bracelet", "bangle",
                    "ring", "hair", "scrunchie", "headband")
                    || containsAny(name, "earring", "jhumka",
                    "necklace", "chain", "choker", "bracelet",
                    "bangle", "ring", "hair", "scrunchie", "headband")) {
                continue;
            }

            int score = accessoryScore(
                    item, base, occasion, weather);

            if (score > bestScore) {
                bestScore = score;
                best = item;
            }
        }

        return best;
    }

    private WardrobeItem bestMatch(
            List<WardrobeItem> items,
            List<WardrobeItem> base,
            String occasion,
            String weather) {

        WardrobeItem best = null;
        int bestScore = Integer.MIN_VALUE;

        for (WardrobeItem item : items) {

            int score = accessoryScore(
                    item, base, occasion, weather);

            if (score > bestScore) {
                bestScore = score;
                best = item;
            }
        }

        return best;
    }

    private int accessoryScore(
            WardrobeItem item,
            List<WardrobeItem> base,
            String occasion,
            String weather) {

        int score = scoreItem(item, occasion, weather);

        for (WardrobeItem baseItem : base) {
            score += colorScore(baseItem, item);
        }

        return score;
    }

    private void printItem(String label, WardrobeItem item) {
        if (item != null) {
            System.out.println(label + ": " + item.getItemName());
        }
    }

    private int occasionScore(WardrobeItem item, String requested) {

        String itemOccasion = item.getOccasion();

        if (itemOccasion != null && !itemOccasion.trim().isEmpty()
                && !isUniversalOccasion(itemOccasion)) {
            return textMatchScore(itemOccasion, requested);
        }

        String suitableFor = item.getSuitableFor();

        if (suitableFor != null && !suitableFor.trim().isEmpty()) {
            return textMatchScore(suitableFor, requested);
        }

        return isUniversalOccasion(itemOccasion) ? 1 : 0;
    }

    private int textMatchScore(String value, String requested) {

        if (value == null || requested == null) return 0;

        String v = normalize(value);
        String r = normalize(requested);

        if (v.equals(r)) return 5;

        String[] values = value.toLowerCase().trim().split("[,;/|]+");

        for (String part : values) {
            String p = normalize(part);

            if (p.equals(r)) return 4;

            if (p.startsWith(r + " ") || p.endsWith(" " + r)) {
                return 3;
            }
        }

        return 0;
    }

    private String normalize(String value) {
        return value.toLowerCase().trim()
                .replaceAll("[^a-z0-9]+", " ").trim();
    }

    private boolean isUniversalOccasion(String value) {

        String v = lower(value);

        return v.equals("all") || v.equals("any")
                || v.equals("all occasions") || v.equals("everyday");
    }

    private boolean weatherMatches(String itemWeather, String requested) {

        if (requested == null) return false;

        String requestedValue = lower(requested);

        // All Weather means no weather restriction.
        if (requestedValue.equals("all weather")
                || requestedValue.equals("all")
                || requestedValue.equals("any")) {
            return true;
        }

        if (itemWeather == null) return false;

        String itemValue = lower(itemWeather);

        return itemValue.equals(requestedValue)
                || itemValue.equals("all weather")
                || itemValue.equals("all")
                || itemValue.equals("any");
    }

    private String lower(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }

    private boolean isFootwear(String category, String type) {
        return category.equals("footwear")
                || containsAny(type, "shoe", "sneaker", "boot",
                        "sandal", "flat", "heel", "loafer", "slipper");
    }

    private boolean isBag(String category, String type) {
        return category.equals("bag")
                || containsAny(type, "bag", "backpack", "purse", "tote");
    }

    private boolean isDress(String type) {
        return containsAny(type, "dress", "gown", "jumpsuit", "romper");
    }

    private boolean isBottom(String type) {
        return containsAny(type, "pant", "trouser", "jean", "short",
                "skirt", "legging", "jogger", "bottom", "culotte");
    }

    private boolean isTop(String type) {
        return containsAny(type, "shirt", "t-shirt", "tshirt", "tee",
                "top", "blouse", "hoodie", "sweater", "sweatshirt",
                "kurti", "tunic", "jacket", "cardigan", "vest");
    }

    private boolean containsAny(String value, String... words) {
        for (String word : words) {
            if (value.contains(word)) return true;
        }
        return false;
    }

    private int scoreItem(WardrobeItem item, String occasion, String weather) {

        int score = occasionScore(item, occasion) * 5;

        if (matchesExact(item.getWeatherType(), weather)) {
            score += 3;
        }

        if (matchesExact(item.getComfortLevel(), "High")) {
            score += 2;
        } else if (matchesExact(item.getComfortLevel(), "Medium")) {
            score += 1;
        }

        return score;
    }

    private boolean matchesExact(String value, String requested) {
        return value != null && requested != null
                && value.trim().equalsIgnoreCase(requested.trim());
    }

    private int colorScore(WardrobeItem first, WardrobeItem second) {

        String a = lower(first.getColor());
        String b = lower(second.getColor());

        if (a.isEmpty() || b.isEmpty() || a.equals(b)) return 0;

        if (isNeutral(a) || isNeutral(b)) return 2;

        return 0;
    }

    private boolean isNeutral(String color) {
        return containsAny(color, "black", "white", "grey", "gray",
                "beige", "cream", "brown", "navy", "denim");
    }
}
