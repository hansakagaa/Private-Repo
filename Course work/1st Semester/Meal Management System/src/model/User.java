package model;

public class User {
    private String userId;
    private String stId;
    private String password;
    private String passwordHint;

    public User() {
    }

    public User(String userId, String stId, String password, String passwordHint) {
        this.userId = userId;
        this.stId = stId;
        this.password = password;
        this.passwordHint = passwordHint;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }
}
