package multiuserdungeon.map;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PremadeMap implements GameMap {

	private final List<Player> players;
	private final List<Room> rooms;
	private final Map<Integer, Integer> playerRooms;
	private final Map<Integer, Integer> playerStartRooms;
	private final int goalRoom;

	public PremadeMap(List<Room> rooms, List<Player> players, Map<Integer, Integer> playerRooms, Map<Integer, Integer> playerStartRooms, int goalRoom) {
		this.rooms = rooms;
		this.players = players;
		this.playerRooms = playerRooms;
		this.playerStartRooms = playerStartRooms;
		this.goalRoom = goalRoom;
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
		// Do nothing
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

	public Room getGoal() {
		return this.rooms.get(this.goalRoom);
	}

	public boolean playerReachedGoal() {
		return getPlayerRoom().equals(getGoal());
	}

}