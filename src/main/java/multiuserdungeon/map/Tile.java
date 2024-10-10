package multiuserdungeon.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import multiuserdungeon.map.tiles.*;
import multiuserdungeon.map.tiles.trap.Trap;

public class Tile {

	private final int row;
	private final int col;
	private final LinkedList<TileObject> objects;
	private Map<Compass, Tile> adjacent;

	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		this.objects = new LinkedList<>();
		EmptyTile emptyTile = new EmptyTile();
		emptyTile.setTile(this);
		this.objects.add(emptyTile);
		this.adjacent = null;
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public Tile getTile(Compass compass) {
		return this.adjacent.get(compass);
	}

	public List<TileObject> getObjects() {
		return this.objects;
	}

	public void addObject(TileObject object) {
		this.objects.add(object);
	}

	public void removeObject(TileObject object) {
		this.objects.remove(object);
	}

	public boolean passable() {
		for(TileObject object : this.objects) {
			if(!object.passable()) {
				return false;
			}
		}

		return true;
	}

	public Map<Compass, Tile> getAdjacent() {
		return this.adjacent;
	}

	public void setAdjacent(Map<Compass, Tile> adjacent) {
		this.adjacent = adjacent;
	}

	public char getASCII() {
		return this.objects.getLast().getASCII();
	}

	public Player getPlayer() {
		for (TileObject object : objects) {
			if (object instanceof Player player) {
				return player;
			}
		}

		return null;
	}

	public NPC getNPC() {
		for (TileObject object : objects) {
			if (object instanceof NPC npc) {
				return npc;
			}
		}

		return null;
	}

	public Trap getTrap() {
		for (TileObject object : objects) {
			if (object instanceof Trap trap) {
				return trap;
			}
		}

		return null;
	}

	public Chest getChest() {
		for(TileObject object : objects) {
			if(object instanceof Chest chest) {
				return chest;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return this.objects.getLast().toString();
	}

}