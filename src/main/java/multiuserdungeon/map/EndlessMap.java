package multiuserdungeon.map;

import java.util.Map;
import java.util.HashMap;
import multiuserdungeon.map.tiles.*;
import multiuserdungeon.QuintonsMagicFolder.*;

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

    public void handleExitRoom(Compass direction) {
        Room room = getPlayerRoom();
        try {
            if (room.getConnections().get(currentPlayer.getTile()) == null) {
                Room newRoom = RoomGenerator.generateRoom(direction,room);
                room.getConnections().put(currentPlayer.getTile(),newRoom);
            }
        } catch (NullPointerException e) {}
    }

    public boolean isStartRoom() {
        return getPlayerRoom().equals(playerStartRooms.get(currentPlayer));
    }

}
