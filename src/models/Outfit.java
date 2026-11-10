
package models;

public class Outfit {

    private int outfitId;
    private int userId;
    private String outfitName;
    private String occasion;
    private String weatherType;
    private String style;

    public Outfit(int userId, String outfitName,
                  String occasion, String weatherType,
                  String style) {
        this.userId = userId;
        this.outfitName = outfitName;
        this.occasion = occasion;
        this.weatherType = weatherType;
        this.style = style;
    }

    public int getOutfitId() {
        return outfitId;
    }

    public void setOutfitId(int outfitId) {
        this.outfitId = outfitId;
    }

    public int getUserId() {
        return userId;
    }

    public String getOutfitName() {
        return outfitName;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public String getStyle() {
        return style;
    }
}
