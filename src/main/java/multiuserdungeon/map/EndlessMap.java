package multiuserdungeon.map;

import java.util.Map;
import multiuserdungeon.map.tiles.*;

public class EndlessMap implements GameMap {
    private Map<Player,Room> playerRooms;
    private Map<Player,Room> playerStartRooms;
    private Player currentPlayer;

    public EndlessMap() {

    }

    @Override
    public Room getPlayerRoom () {
        return playerRooms.get(currentPlayer);
    }
    @Override
    public void setPlayerRoom (Room room) {
        this.playerRooms.put(currentPlayer,room);
    }

    public void handleExitRoom() {
        Room room = getPlayerRoom();
        if (room.getConnections().get(currentPlayer.getTile()) == null) {
            //TODO{method here for generating new rooms}
        }
        
    }

    public boolean isStartRoom() {
        return getPlayerRoom().equals(playerStartRooms.get(currentPlayer));
    }
}
