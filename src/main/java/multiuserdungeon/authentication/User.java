package multiuserdungeon.authentication;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.Player;

public class User {

    private Player player;

    public User(String name, String desc) {
        this.player = new Player(name, desc);
    }
    
    public User() {
        this.player = new Player("Guest", "A guest user...");
    }

    public Player getPlayer() {
        return this.player;
    }

    public void handleBrowseMap() {
        // TODO: choose room and put player at start (putting player at goal room for now)
        
    }
}
