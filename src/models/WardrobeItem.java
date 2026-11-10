
package models;

public class WardrobeItem {
    private int itemId;
    private int userId;
    private String itemName;
    private String itemCategory;
    private String itemType;
    private String suitableFor;
    private String color;
    private String material;
    private String occasion;
    private String comfortLevel;
    private String weatherType;
    public WardrobeItem(int userId, String itemName,
            String itemCategory, String itemType,
            String suitableFor, String color,
            String material, String occasion,
            String comfortLevel, String weatherType) {
        this.userId = userId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemType = itemType;
        this.suitableFor = suitableFor;
        this.color = color;
        this.material = material;
        this.occasion = occasion;
        this.comfortLevel = comfortLevel;
        this.weatherType = weatherType;
    }
    public int getItemId() {
        return itemId;
    }
    public int getUserId() {
        return userId;
    }
    public String getItemName() {
        return itemName;
    }
    public String getItemCategory() {
        return itemCategory;
    }
    public String getItemType() {
        return itemType;
    }
    public String getSuitableFor() {
        return suitableFor;
    }
    public String getColor() {
        return color;
    }
    public String getMaterial() {
        return material;
    }
    public String getOccasion() {
        return occasion;
    }
    public String getComfortLevel() {
        return comfortLevel;
    }
    public String getWeatherType() {
        return weatherType;
    }
}
