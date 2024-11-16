package multiuserdungeon.map;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.*;

import java.util.List;

public class EndlessMap implements GameMap {

    private final List<Room> rooms;
    private final Map<Player, Integer> playerRooms;
    private final Map<Player, Integer> playerStartRooms;

    public EndlessMap(List<Room> rooms, Map<Player, Integer> playerRooms, Map<Player, Integer> playerStartRooms) {
        this.rooms = rooms;
        this.playerRooms = playerRooms;
        this.playerStartRooms = playerStartRooms;
    }

    public EndlessMap(EndlessMap oldMap) {
        // TODO: Refactor copy constructor, should not store currentPlayer at all
        this.rooms = new ArrayList<>();
        this.playerRooms = new HashMap<>();
        this.playerStartRooms = new HashMap<>();
    }

    @Override
    public Room getPlayerRoom() {
        return this.rooms.get(this.playerRooms.get(Game.getInstance().getPlayer()));
    }

    @Override
    public void setPlayerRoom(Room room) {
        this.playerRooms.put(Game.getInstance().getPlayer(), this.rooms.indexOf(room));
    }

    @Override
    public boolean isInStartRoom() {
        return getPlayerRoom().equals(rooms.get(this.playerStartRooms.get(Game.getInstance().getPlayer())));
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

}