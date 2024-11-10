package multiuserdungeon.authentication;

import multiuserdungeon.map.tiles.Player;

import java.util.ArrayList;

public class Profile extends User {

    private String password;
    private ArrayList<GameStats> stats;

    public Profile(String username, String password, String desc) {
        super(username, desc);
        this.password = password;
        // TODO: get historic stats from json
        this.stats = new ArrayList<>();
    }

    public String getPassword() {
        return this.password;
    }

    public Player getPlayer() {
        return super.getPlayer();
    }

    public ArrayList<GameStats> getStats() {
        return this.stats;
    }

    public void addToStats(GameStats stats) {
        this.stats.add(stats);
    }

    public void handleNewGame() {
        //TODO: browse a map via whatever format
    }

    public void handleResumeGame(String filename) {
        //TODO: load a saved game via whatever format
    }

}
