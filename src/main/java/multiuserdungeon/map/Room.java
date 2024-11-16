package multiuserdungeon.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.NPC;
import multiuserdungeon.map.tiles.Player;

public class Room {

	private final int rows;
	private final int columns;
	private final String description;
	private final Map<Compass, Tile> doorways;
	private final Map<Tile, Room> connections;
	private final Tile[][] layout;
	private Tile playerTile;

	public Room(int rows, int columns, String description) {
		this.rows = rows;
		this.columns = columns;
		this.description = description;
		this.doorways = new HashMap<>();
		this.connections = new HashMap<>();
		this.layout = new Tile[rows][columns];
		this.playerTile = null;

		for(int row = 0; row < this.rows; row++){
			for(int col = 0; col < this.columns; col++){
				this.layout[row][col] = new Tile(row, col);
			}
		}

		for(int row = 0; row < this.rows; row++) {
			for(int col = 0; col < this.columns; col++) {
				Map<Compass, Tile> adjacent = new HashMap<>();
				for(Compass compass : Compass.values()) {
					Tile adjacentTile = getTile(row + compass.getRowOffset(), col + compass.getColOffset());
					if(adjacentTile == null) continue;
					adjacent.put(compass, adjacentTile);
				}
				getTile(row, col).setAdjacent(adjacent);
			}
		}
	}

	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return this.columns;
	}

	public String getDescription() {
		return this.description;
	}

	public Tile getDoorway(Compass compass) {
		return this.doorways.get(compass);
	}

	public void addConnection(int row, int col, Room targetRoom) {
		Compass direction = null;
		if(row == 0) {
			direction = Compass.NORTH;
		} else if(col == this.columns - 1) {
			direction = Compass.EAST;
		} else if(row == this.rows - 1) {
			direction = Compass.SOUTH;
		} else if(col == 0) {
			direction = Compass.WEST;
		}
		if(direction == null) return;

		Tile tile = getTile(row, col);
		if(tile == null) return;

		this.doorways.put(direction, getTile(row, col));
		this.connections.put(tile, targetRoom);
	}

	public Tile getTile(int row, int col) {
		try {
			return this.layout[row][col];
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Tile getPlayerTile() {
		return this.playerTile;
	}

	public void setPlayerTile(Tile playerTile) {
		this.playerTile = playerTile;
	}

	public boolean isSafe(){
		for(int i = 0; i < layout.length; i++){
			for(int j = 0; j < layout[j].length; j++){
				List<TileObject> tileobjects = layout[i][j].getObjects();
				for(TileObject tileobject : tileobjects){
					if(tileobject instanceof NPC){
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean handleExitRoom(Compass direction) {
		Room newRoom = this.connections.get(this.doorways.get(direction));
		if(newRoom == null) return false;
		Tile newTile = newRoom.getDoorway(direction.getOpposite());
		if(newTile == null) {
			System.out.println("newTile is null");
			return false;
		}

		Player player = Game.getInstance().getPlayer();
		Game.getInstance().getMap().setPlayerRoom(newRoom);
		newRoom.setPlayerTile(newTile);
		player.setTile(newTile);
		this.playerTile = null;

		player.getTile().removeObject(player);
		newTile.addObject(player);
		return true;
	}

	public String getFormattedDescription() {
		String fullDescription = "A " + this.rows + "x" + this.columns + " " + this.description + " with " + this.doorways.size() + " exit(s). You see the following:";

		StringBuilder builder = new StringBuilder();
		builder.append(fullDescription);
		for(int row = 0; row < this.rows; row++) {
			for(int col = 0; col < this.columns; col++) {
				Tile tile = getTile(row, col);
				if(tile.getObjects().size() <= 1) continue;

				builder.append("\n\t- ").append(tile);
			}
		}

		return builder.toString();
	}

	@Override
	public boolean equals(Object object) {
		if(!(object instanceof Room room)) return false;

		return this.rows == room.rows &&
				this.columns == room.columns &&
				this.description.equals(room.description) &&
				this.doorways.size() == room.doorways.size() &&
				this.connections.size() == room.connections.size();
	}

	@Override
	public String toString() {
		StringBuilder columnNums = new StringBuilder("  ");
		for(int col = 0; col < this.columns; col++) {
			columnNums.append(col).append(" ");
		}
		columnNums.append("\n");

		StringBuilder builder = new StringBuilder(columnNums.toString());
		for(int row = 0; row < this.rows; row++) {
			builder.append(row).append(" ");
			for(int col = 0; col < this.columns; col++) {
				Tile tile = getTile(row, col);
				if(tile.getASCII() == '-' && this.connections.containsKey(tile)) {
					builder.append('D');
				} else {
					builder.append(tile.getASCII());
				}
				builder.append(" ");
			}
			builder.append("\n");
		}
		return builder.toString();
	}

	public Map<Tile,Room> getConnections () {
		return this.connections;
	}

}
