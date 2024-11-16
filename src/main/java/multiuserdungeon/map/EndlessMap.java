package multiuserdungeon.map;

import java.util.Map;
import java.util.HashMap;
import multiuserdungeon.map.tiles.*;
import multiuserdungeon.RoomGenerator;
import java.util.List;

public class EndlessMap implements GameMap {
    private Map<Player,Room> playerRooms;
    private Map<Player,Room> playerStartRooms;
    private Player currentPlayer;

    private List<Room> rooms;

    public EndlessMap(Player player, List<Room> rooms) {
        this.playerRooms = new HashMap<>();
        this.playerStartRooms = new HashMap<>();
        this.currentPlayer = player;
        this.rooms = rooms;
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
                rooms.add(newRoom);
                room.getConnections().put(currentPlayer.getTile(),newRoom);
            }
        } catch (NullPointerException e) {}
    }

    public boolean isStartRoom() {
        return getPlayerRoom().equals(playerStartRooms.get(currentPlayer));
    }

    @Override
    public List<Room> getRooms() {
        return rooms;
    }

}
