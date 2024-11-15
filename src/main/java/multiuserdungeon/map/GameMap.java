package multiuserdungeon.map;

import java.util.List;

public interface GameMap {
    public Room getPlayerRoom();
    public void setPlayerRoom(Room room);
    public List<Room> getRooms();
}