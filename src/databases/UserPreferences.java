
package databases;

public class UserPreferences {

    private int userId;
    private String preferredStyle;
    private String preferredColor;
    private String preferredFit;
    private String preferredComfort;
    private String preferredMaterial;

    public UserPreferences(int userId, String preferredStyle,
            String preferredColor, String preferredFit,
            String preferredComfort, String preferredMaterial) {

        this.userId = userId;
        this.preferredStyle = preferredStyle;
        this.preferredColor = preferredColor;
        this.preferredFit = preferredFit;
        this.preferredComfort = preferredComfort;
        this.preferredMaterial = preferredMaterial;
    }

    public int getUserId() {
        return userId;
    }

    public String getPreferredStyle() {
        return preferredStyle;
    }

    public String getPreferredColor() {
        return preferredColor;
    }

    public String getPreferredFit() {
        return preferredFit;
    }

    public String getPreferredComfort() {
        return preferredComfort;
    }

    public String getPreferredMaterial() {
        return preferredMaterial;
    }
}
