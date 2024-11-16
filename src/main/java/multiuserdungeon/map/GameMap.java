package multiuserdungeon.map;

import java.util.List;

public interface GameMap {

    Room getPlayerRoom();
    void setPlayerRoom(Room room);
    List<Room> getRooms();

}
