package multiuserdungeon.map.tiles.shrine;

import multiuserdungeon.clock.Clock;
import multiuserdungeon.map.Map;
import multiuserdungeon.map.tiles.Player;

public class Snapshot {
    private Player player;
    private Map map;
    private Clock clock;

    public Snapshot(Player player, Map map, Clock clock){
        this.player = player;
        this.map = map;
        this.clock = clock;
    }

    public Player getPlayer(){
        return player;
    }

    public Map getMap() {
        return map;
    }
    public Clock getClock() {
        return clock;
    }

    
}
