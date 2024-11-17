package multiuserdungeon.map;

import multiuserdungeon.map.tiles.Player;

import java.util.List;
import java.util.Map;

public interface GameMap {

    Room getPlayerRoom();

    void setPlayerRoom(Room room);

    boolean isInStartRoom();

    void handleExitRoom(Compass direction);

    List<Room> getRooms();

    List<Player> getPlayers();

    Map<Integer, Integer> getPlayerRooms();

    Map<Integer, Integer> getPlayerStartRooms();

}
