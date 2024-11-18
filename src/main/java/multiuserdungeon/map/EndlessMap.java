package multiuserdungeon.map;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.*;

import java.util.List;

public class EndlessMap implements GameMap {

    private final List<Player> players;
    private final List<Room> rooms;
    private final Map<Integer, Integer> playerRooms;
    private final Map<Integer, Integer> playerStartRooms;

    public EndlessMap(List<Room> rooms, List<Player> players, Map<Integer, Integer> playerRooms, Map<Integer, Integer> playerStartRooms) {
        this.rooms = rooms;
        this.players = players;
        this.playerRooms = playerRooms;
        this.playerStartRooms = playerStartRooms;
    }

    public EndlessMap(EndlessMap oldMap) {
        this.players = new ArrayList<>();
        for(Player player : oldMap.players) {
            this.players.add(new Player(player));
        }
        this.rooms = new ArrayList<>();
        for(Room room : oldMap.rooms) {
            this.rooms.add(new Room(room));
        }
        for(Room oldRoom : oldMap.rooms) {
            int oldRoomId = oldMap.rooms.indexOf(oldRoom);
            Room fromRoom = this.rooms.get(oldRoomId);
            for(Map.Entry<Tile, Room> connection : oldRoom.getConnections().entrySet()) {
                int toRoomId = oldMap.rooms.indexOf(connection.getValue());
                if(toRoomId == -1) {
                    fromRoom.addConnection(connection.getKey().getRow(), connection.getKey().getCol(), null);
                } else {
                    Room toRoom = this.rooms.get(toRoomId);
                    fromRoom.addConnection(connection.getKey().getRow(), connection.getKey().getCol(), toRoom);
                }
            }
        }
        this.playerRooms = new HashMap<>(oldMap.playerRooms);
        this.playerStartRooms = new HashMap<>(oldMap.playerStartRooms);
    }

    @Override
    public Room getPlayerRoom() {
        return this.rooms.get(this.playerRooms.get(this.players.indexOf(Game.getInstance().getPlayer())));
    }

    @Override
    public void setPlayerRoom(Room room) {
        this.playerRooms.put(this.players.indexOf(Game.getInstance().getPlayer()), this.rooms.indexOf(room));
    }

    @Override
    public boolean isInStartRoom() {
        return getPlayerRoom().equals(this.rooms.get(this.playerStartRooms.get(this.players.indexOf(Game.getInstance().getPlayer()))));
    }

    @Override
    public void handleExitRoom(Compass direction) {
        Room room = getPlayerRoom();
        Tile tile = Game.getInstance().getPlayer().getTile();
        if(!room.isConnectionLoaded(tile)) {
            Room newRoom = RoomGenerator.generateRoom(direction, room);
            room.addConnection(tile.getRow(), tile.getCol(), newRoom);
            this.rooms.add(newRoom);
        }
    }

    @Override
    public List<Room> getRooms() {
        return this.rooms;
    }

    @Override
    public List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public Map<Integer, Integer> getPlayerRooms() {
        return this.playerRooms;
    }

    @Override
    public Map<Integer, Integer> getPlayerStartRooms() {
        return this.playerStartRooms;
    }

}