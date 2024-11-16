package multiuserdungeon.map;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PremadeMap implements GameMap {

	private final List<Room> rooms;
	private final Map<Player, Integer> playerRooms;
	private final Map<Player, Integer> playerStartRooms;
	private final int goalRoom;

	public PremadeMap(List<Room> rooms, Map<Player, Integer> playerRooms, Map<Player, Integer> playerStartRooms, int goalRoom) {
		this.rooms = rooms;
		this.playerRooms = playerRooms;
		this.playerStartRooms = playerStartRooms;
		this.goalRoom = goalRoom;
	}

	public PremadeMap(PremadeMap oldMap) {
		// TODO: Refactor copy constructor, should not store currentPlayer at all
		this.rooms = new ArrayList<>();
		this.playerRooms = new HashMap<>();
		this.playerStartRooms = new HashMap<>();
		this.goalRoom = oldMap.goalRoom;
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
		// Do nothing
	}

	public Room getGoal() {
		return this.rooms.get(this.goalRoom);
	}

	public boolean playerReachedGoal() {
		return getPlayerRoom().equals(getGoal());
	}

}