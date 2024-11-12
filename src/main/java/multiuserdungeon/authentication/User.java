package multiuserdungeon.authentication;

public class User {

    private final String username;
    private final String description;

    public User(String name, String description) {
        this.username = name;
        this.description = description;
    }

    public String getUsername() {
        return this.username;
    }

    public String getDescription() {
        return this.description;
    }

    public void handleBrowseMap() {
        // TODO: choose room and put player at start
    }

}
