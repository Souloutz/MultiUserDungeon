package multiuserdungeon.authentication;

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

    public boolean changePassword(String prevPassword, String newPassword) {
        if (prevPassword.equals(this.password)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public ArrayList<GameStats> getStats() {
        return this.stats;
    }

    public void addToStats(GameStats stats) {
        this.stats.add(stats);
    }

    public void handleNewGame(String mapType, String filePath) {
        //TODO: start a new game of either endless or premade maps

    }

    public void handleResumeGame(String filename) {
        //TODO: load a saved game via whatever format
    }

    public boolean handleJoinGame(String filename) {
        // TODO persistence loadMap
        return false;
    }
}
