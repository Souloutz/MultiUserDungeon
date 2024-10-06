package multiuserdungeon.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.Player;

public class Room {

	private final int length;
	private final int width;
	private final Map<Compass, Tile> doorways;
	private final Map<Tile, Room> connections;
	private final Tile[][] layout;
	private Tile playerTile;

	public Room(int width, int length) {
		this.length = length;
		this.width = width;
		this.doorways = new HashMap<>();
		this.connections = new HashMap<>();
		this.layout = new Tile[width][length];
		this.playerTile = null;

		for(int x = 0; x < width; x++){
			for(int y = 0; y < length; y++){
				this.layout[x][y] = new Tile(x,y);
			}
		}
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < length; y++) {
				Map<Compass,Tile> adjacent = new HashMap<>();
				for (Compass compass : Compass.values()) {
					adjacent.put(compass,getTile(x + compass.getX(), y + compass.getY()));
				}
				getTile(x,y).setAdjacent(adjacent);
			}
		}
	}

	public int getWidth() {
		return this.width;
	}

	public int getLength() {
		return this.length;
	}

	public Map<Compass, Tile> getDoorways() {
		return doorways;
	}

	public Map<Tile, Room> getConnections() {
		return connections;
	}

	public Tile getTile(int x, int y) {
		try {
			return this.layout[x][y];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Tile[][] getLayout() {
		return this.layout;
	}

	private Tile[] getAllTiles() {
		return Arrays.stream(this.layout).flatMap(Arrays::stream).toArray(Tile[]::new);
	}

	public Tile getPlayerTile() {
		return this.playerTile;
	}

	public void setPlayerTile(Tile playerTile) {
		this.playerTile = playerTile;
	}

	public boolean handleExitRoom(Compass direction) {
		Player player = Game.getInstance().getPlayer();

		Room newRoom = this.connections.get(this.doorways.get(direction));
		if(newRoom == null) return false;
		Tile newTile = newRoom.getDoorways().get(direction.getOpposite());
		if(newTile == null) return false;
		newRoom.setPlayerTile(newTile);

		player.getTile().removeObjects();
		newTile.addObject(player);
		player.setTile(newTile);
		this.playerTile = null;

		return true;
	}

	@Override
	public boolean equals(Object otherRoom) {
		if (!(otherRoom instanceof Room))
			return false;

		// TODO
		return true;
	}

	@Override
	public String toString() {
		String room = "";
		for (Tile tile : getAllTiles()) {
			room += tile + ", ";
			if (tile.x == width - 1) {
				room += "\n";
			}
		}
		return room;
	}

}
