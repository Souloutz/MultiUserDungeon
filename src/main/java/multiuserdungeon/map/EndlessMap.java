package multiuserdungeon.map;

import java.util.Map;
import java.util.HashMap;
import multiuserdungeon.map.tiles.*;

public class EndlessMap implements GameMap {
    private Map<Player,Room> playerRooms;
    private Map<Player,Room> playerStartRooms;
    private Player currentPlayer;

    public EndlessMap(Player player) {
        this.playerRooms = new HashMap<>();
        this.playerStartRooms = new HashMap<>();
        this.currentPlayer = player;
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
        try {
            if (room.getConnections().get(currentPlayer.getTile()) == null) {
                //TODO{method here for generating new rooms}
            }
        } catch (NullPointerException e) {}
    }

    public boolean isStartRoom() {
        return getPlayerRoom().equals(playerStartRooms.get(currentPlayer));
    }

    private static class RoomGenerator {
        
    }
}
