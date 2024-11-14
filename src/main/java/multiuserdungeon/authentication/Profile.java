package multiuserdungeon.authentication;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;

import multiuserdungeon.Game;
import multiuserdungeon.persistence.GameStatsCSVConverter;
import multiuserdungeon.persistence.PersistenceManager;

import java.util.ArrayList;
import java.util.List;

public class Profile extends User {

    @CsvBindByPosition(position = 2)
    private String password;
    @CsvBindAndSplitByPosition(position = 3, elementType = GameStats.class, writeDelimiter = "|", converter = GameStatsCSVConverter.class)
    private final List<GameStats> stats;

    public Profile() {
        super(null, null);
        this.password = null;
        this.stats = null;
    }

    public Profile(String username, String password, String description) {
        super(username, description);
        this.password = password;
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

    public List<GameStats> getStats() {
        return this.stats;
    }

    public void addToStats(GameStats stats) {
        this.stats.add(stats);
    }

    public void handleNewGame(String mapType, String filePath) {
        // TODO: start a new game of either endless or premade maps
        Game game = PersistenceManager.getInstance().loadGame(filePath);
    }

    public void handleResumeGame(String filename) {
        // TODO: load a saved game via whatever format
        Game game = PersistenceManager.getInstance().loadGame(filename);
    }

    public boolean handleJoinGame(String filename) {
        // TODO create a new room connected via unexplored exit and use that as player starting room
        Game game = PersistenceManager.getInstance().loadGame(filename);
        return false;
    }
}
