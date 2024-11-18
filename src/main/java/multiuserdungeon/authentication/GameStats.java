package multiuserdungeon.authentication;

public class GameStats {

    private int gamesPlayed;
    private int livesLost;
    private int monstersSlain;
    private int goldEarned;
    private int itemsFound;

    public GameStats() {
        this.gamesPlayed = 0;
        this.livesLost = 0;
        this.monstersSlain = 0;
        this.goldEarned = 0;
        this.itemsFound = 0;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getLivesLost() {
        return this.livesLost;
    }

    public int getMonstersSlain() {
        return this.monstersSlain;
    }

    public int getGoldEarned() {
        return this.goldEarned;
    }

    public int getItemsFound() {
        return this.itemsFound;
    }

    public void addAll(GameStats stats) {
        this.gamesPlayed += stats.getGamesPlayed();
        this.livesLost += stats.getLivesLost();
        this.monstersSlain += stats.getMonstersSlain();
        this.goldEarned += stats.getGoldEarned();
        this.itemsFound += stats.getItemsFound();
    }

    public void addToGamesPlayed(int n) {
        this.gamesPlayed += n;
    }
    
    public void addToLivesLost(int n) {
        this.livesLost += n;
    }

    public void addToMonstersSlain(int n) {
        this.monstersSlain += n;
    }

    public void addToGoldEarned(int n) {
        this.goldEarned += n;
    }

    public void addToItems(int n) {
        this.itemsFound += n;
    }

}
