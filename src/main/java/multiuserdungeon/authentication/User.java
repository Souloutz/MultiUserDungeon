package multiuserdungeon.authentication;

import com.opencsv.bean.CsvBindByPosition;

public class User {

    @CsvBindByPosition(position = 0)
    private final String username;
    @CsvBindByPosition(position = 1)
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

    public void handleBrowseMap(String filePath) {
        // TODO: choose room and put player at start
    }

}
