package multiuserdungeon.map;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.*;

import java.util.List;

public class EndlessMap implements GameMap {

    private final List<Room> rooms;
    private final List<Player> players;
    private final Map<Integer, Integer> playerRooms;
    private final Map<Integer, Integer> playerStartRooms;

    public EndlessMap(List<Room> rooms, List<Player> players, Map<Integer, Integer> playerRooms, Map<Integer, Integer> playerStartRooms) {
        this.rooms = rooms;
        this.players = players;
        this.playerRooms = playerRooms;
        this.playerStartRooms = playerStartRooms;
    }

    public EndlessMap(EndlessMap oldMap) {
        // TODO: Refactor copy constructor, should not store currentPlayer at all
        this.rooms = new ArrayList<>();
        this.players = new ArrayList<>();
        this.playerRooms = new HashMap<>();
        this.playerStartRooms = new HashMap<>();
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