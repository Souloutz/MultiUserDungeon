package multiuserdungeon.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import multiuserdungeon.map.tiles.*;
import multiuserdungeon.map.tiles.trap.Trap;

public class Tile {

	public final int x;
	public final int y;
	private List<TileObject> objects;
	private Map<Compass, Tile> adjacent;

	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
		this.objects = new ArrayList<>();
		this.adjacent = new HashMap<>();
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
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

	public void removeObjects() {
		this.objects = new ArrayList<>();
		EmptyTile empty = new EmptyTile();
		empty.setTile(this);
		this.objects.add(empty);
	}

	public boolean passable() {
		for(TileObject object : this.objects) {
			if(!object.passable()) {
				return false;
			}
		}

		return true;
	}

	public List<Tile> getAdjacent() {
		return this.adjacent.values().stream().toList();
	}

	public void setAdjacent(Map<Compass, Tile> adjacent) {
		this.adjacent = adjacent;
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
		for (TileObject object : objects) {
			if (object instanceof Chest chest) {
				return chest;
			}
		}

		return null;
	}

}