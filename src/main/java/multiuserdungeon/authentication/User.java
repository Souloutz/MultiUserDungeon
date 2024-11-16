package multiuserdungeon.authentication;

import com.opencsv.bean.CsvBindByPosition;

import multiuserdungeon.Game;
import multiuserdungeon.persistence.PersistenceManager;

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
        PersistenceManager.getInstance().loadGame(filePath);
    }

}
