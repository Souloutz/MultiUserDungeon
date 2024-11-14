package multiuserdungeon.map;

import java.util.Set;

public interface GameMap {
    public Room getPlayerRoom();
    public void setPlayerRoom(Room room);
    public Set<Room> getRooms();
}
