package multiuserdungeon.map;

import java.util.Map;
import multiuserdungeon.map.tiles.*;

public class EndlessMap implements GameMap {
    private Map<String,Room> playerRooms;
    private Player currentPlayer;

    public EndlessMap() {

    }

    public Room getPlayerRoom () {
        return playerRooms.get(currentPlayer.getName());
    }
    public void setPlayerRoom (Room room) {
        this.playerRooms.put(currentPlayer.getName(),room);
    }

    public void handleExitRoom() {
        
    }
}
