package multiuserdungeon.authentication;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.Player;

public class User {

    private String username;
    private String desc;

    public User(String name, String desc) {
        this.username = name;
        this.desc = desc;
    }

    public String getUsername() {return this.username;}

    public String getDesc() {return this.desc;}

    public void handleBrowseMap(String filePath) {
        // TODO: choose room and put player at start
    }
}
