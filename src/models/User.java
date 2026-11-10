
package models;

public class User {
    private int userId;
    private String userName;
    private String email;
    private String passwordHash;

    public User(int userId, String userName,
                String email, String passwordHash) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
    }
    public int getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
}
