package multiuserdungeon.map;

public interface GameMap {

    Room getPlayerRoom();

    void setPlayerRoom(Room room);

    boolean isInStartRoom();

    void handleExitRoom(Compass direction);

}
