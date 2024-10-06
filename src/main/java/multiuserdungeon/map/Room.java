package multiuserdungeon.map;

import java.util.HashMap;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.Player;

public class Room implements Iterable<Tile> {

	private int length;
	private int width;
	private HashMap<Compass,Tile> doorways;
	private HashMap<Tile,Room> connections;
	private Tile[][] layout;

	public Room(int width, int length) {
		layout = new Tile[width][length];
		this.length = length;
		this.width = width;
		this.doorways = new HashMap<Compass,Tile>();
		this.connections = new HashMap<Tile,Room>();
		for (int x = 0; x < width; x++){
			for (int y = 0; y < length; y++){
				layout[x][y] = new Tile(x,y,new HashMap<>());
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < length; y++) {
				HashMap<Compass,Tile> adjacent = new HashMap<>();
				for (Compass compass : Compass.values()) {
					adjacent.put(compass,getTile(x + compass.getX(), y + compass.getY()));
				}
				getTile(x,y).setAdjacent(adjacent);
			}
		}
	}

	public RoomIterator iterator() {
		return new RoomIterator(this);
	}

	public Tile getTile(int x, int y) {
		try {
			return layout[x][y];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	//Whole function will be better utilized once we are reading from json files
	public void populate(TileObject[][] tiles) {
		// to fill with random TileObjects
	}

	public void handleExitRoom(Compass direction) {
		Player player = Game.getInstance().getPlayer();
		Room newRoom = connections.get(doorways.get(direction));
		Tile oldTile = player.getTile();
		Tile newTile = newRoom.getDoorways().get(direction.getOpposite());
		oldTile.getObjects().remove(player);
		newTile.getObjects().add(player);
		player.setTile(newTile);
	}

	public Tile getPlayerTile() {
		for (Tile tile : this) {
			if (tile.hasPlayer()) {
				return tile;
			}
		}
		return null;
	}

	public void setPlayerTile(Player player, int x, int y) {
		Tile tile = getTile(x,y);
		tile.addObject(player);
	}

	public HashMap<Compass,Tile> getDoorways () {
		return doorways;
	}

	public HashMap<Tile, Room> getConnections() {
		return connections;
	}

	public boolean isDoorway (Tile tile) {
		for(Tile doorway : doorways.values()) {
			if (tile == doorway) {
				return true;
			}
		}
		return false;
	}

	public Tile[][] getLayout() {
		return layout;
	}

	public Map<Tile, Room> getConnections() {
		return null;
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
		for (Tile tile : this) {
			room += tile + ", ";
			if (tile.x == width - 1) {
				room += "\n";
			}
		}
		return room;
	}
}
