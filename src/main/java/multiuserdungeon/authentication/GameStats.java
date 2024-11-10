package multiuserdungeon.authentication;

public class GameStats {

    private int gamesPlayed;
    private int livesLost;
    private int monstersSlain;
    private int totalGold;
    private int itemsFound;

    public GameStats() {
        this.gamesPlayed = 0;
        this.livesLost = 0;
        this.monstersSlain = 0;
        this.totalGold = 0;
        this.itemsFound = 0;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getLivesLost() {
        return livesLost;
    }

    public int getMonstersSlain() {
        return monstersSlain;
    }

    public int getTotalGold() {
        return totalGold;
    }

    public int getItemsFound() {
        return itemsFound;
    }

    public void addToGames(int n) {
        this.gamesPlayed += n;
    }

    public void addToLives(int n) {
        this.livesLost += n;
    }

    public void addToMonsters(int n) {
        this.monstersSlain += n;
    }

    public void addToGold(int n) {
        this.totalGold += n;
    }

    public void addToItems(int n) {
        this.itemsFound += n;
    }
}
