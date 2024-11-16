package multiuserdungeon.map.tiles.shrine;

import multiuserdungeon.clock.Clock;
import multiuserdungeon.map.GameMap;
import multiuserdungeon.map.tiles.Player;

public class Snapshot {
    private Player player;
    private GameMap map;
    private Clock clock;

    public Snapshot(Player player, GameMap map, Clock clock){
        this.player = player;
        this.map = map;
        this.clock = clock;
    }

    public Player getPlayer(){
        return player;
    }

    public GameMap getMap() {
        return map;
    }
    public Clock getClock() {
        return clock;
    }

    
}
