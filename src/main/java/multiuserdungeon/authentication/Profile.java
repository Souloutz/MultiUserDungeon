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

    public Profile(String username, String description, String password) {
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

    public String viewHistory() {
        StringBuilder sb = new StringBuilder();
        GameStats totals = new GameStats();
        for(int i = 0; i < this.stats.size(); i++) {
            GameStats stat = this.stats.get(i);
            totals.addAll(stat);
            sb.append("SESSION #").append(i + 1)
                    .append("\n\tGames Played: ").append(stat.getGamesPlayed())
                    .append("\n\tLives Lost: ").append(stat.getLivesLost())
                    .append("\n\tMonsters Slain: ").append(stat.getMonstersSlain())
                    .append("\n\tGold Earned: ").append(stat.getGoldEarned())
                    .append("\n\tItems Found: ").append(stat.getItemsFound()).append("\n");
        }
        sb.append("TOTALS")
                .append("\n\tGames Played: ").append(totals.getGamesPlayed())
                .append("\n\tLives Lost: ").append(totals.getLivesLost())
                .append("\n\tMonsters Slain: ").append(totals.getMonstersSlain())
                .append("\n\tGold Earned: ").append(totals.getGoldEarned())
                .append("\n\tItems Found: ").append(totals.getItemsFound()).append("\n");
        return sb.toString().trim();
    }

    public void addToStats(GameStats stats) {
        if(this.stats == null) return;
        this.stats.add(stats);
    }

    public Game handleStartGame(String filename) {
        return PersistenceManager.getInstance().loadGame(filename);
    }

    public Game handleJoinGame(String filename) {
        return PersistenceManager.getInstance().loadGame(filename);
    }

}
