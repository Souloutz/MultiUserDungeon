package multiuserdungeon.map.tiles.shrine;

import multiuserdungeon.authentication.GameStats;
import multiuserdungeon.clock.Clock;
import multiuserdungeon.map.GameMap;
import multiuserdungeon.map.tiles.Player;

public class Snapshot {

    private final Player player;
    private final GameMap map;
    private final Clock clock;
    private final GameStats stats;

    public Snapshot(Player player, GameMap map, Clock clock, GameStats stats) {
        this.player = player;
        this.map = map;
        this.clock = clock;
        this.stats = stats;
    }

    public Player getPlayer(){
        return this.player;
    }

    public GameMap getMap() {
        return this.map;
    }

    public Clock getClock() {
        return this.clock;
    }

    public GameStats getStats() {
        return this.stats;
    }

}
