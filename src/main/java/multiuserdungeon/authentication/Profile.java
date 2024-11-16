package multiuserdungeon.authentication;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.Player;
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
        if(prevPassword.equals(this.password)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public List<GameStats> getStats() {
        return this.stats;
    }

    public void addToStats(GameStats stats) {
        if(this.stats == null) return;
        this.stats.add(stats);
    }

    public Game handleStartGame(String filename) {
        return PersistenceManager.getInstance().loadGame(filename);
    }

    public Game handleJoinGame(String filename) {
        Game game = PersistenceManager.getInstance().loadGame(filename);
        game.setPlayer(new Player(getUsername(), "A player."));
        // TODO: place player in new attached room
        return game;
    }

}
