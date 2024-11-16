package multiuserdungeon.map.tiles.shrine;

import multiuserdungeon.clock.Clock;
import multiuserdungeon.map.GameMap;
import multiuserdungeon.map.tiles.Player;

public class Snapshot {

    private final Player player;
    private final GameMap map;
    private final Clock clock;

    public Snapshot(Player player, GameMap map, Clock clock){
        this.player = player;
        this.map = map;
        this.clock = clock;
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

}
