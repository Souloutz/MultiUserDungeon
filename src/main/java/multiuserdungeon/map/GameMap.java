package multiuserdungeon.map;

public interface GameMap {

    Room getPlayerRoom();

    void setPlayerRoom(Room room);

    void handleExitRoom(Compass direction);

}
