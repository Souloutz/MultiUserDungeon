package multiuserdungeon.map;

import java.util.ArrayList;
import java.util.Map;
import multiuserdungeon.map.tiles.*;

public class Tile {

	
	private ArrayList<TileObject> objects;
	private Map<Compass,Tile> adjacent;
	public int x;
	public int y;

	public Tile(int x, int y, Map<Compass,Tile> adjacent) {
		this.x = x;
		this.y = y;
		this.objects = new ArrayList<TileObject>();
		this.adjacent = adjacent;

	}

	public Tile getTile(Compass compass){
		int i = 0;
		for (Compass dir : Compass.values()){
			if (dir == compass){
				return this.getAdjacent().get(i);
			}
			i++;
		}
		return null;
	}

	public ArrayList<TileObject> getObjects() {
		return objects;
	}

	public void addObject(TileObject object) {
		objects.add(object);
	}

	public void removeObjects() {
		objects = new ArrayList<TileObject>();
	}

	public String getObjectName() {
		String names = "";
		for (TileObject object: objects) {
			names += object.getName();
		}
		return names;
	}

	public boolean hasPlayer() {
		for (TileObject object: objects) {
			if (object instanceof Player) {
				return true;
			}
		}

		return false;
	}

	public boolean hasTrap() {
		for (TileObject object: objects) {
			if (object.isTrap()) {
				return true;
			}
		}

		return false;
	}

	public boolean hasNPC() {
		for (TileObject object: objects) {
			if (object instanceof NPC) {
				return true;
			}
		}

		return false;
	}

	public boolean hasChest() {
		for (TileObject object: objects) {
			if (object instanceof Chest) {
				return true;
			}
		}

		return false;
	}

	public boolean passable() {
		for (TileObject object: objects) {
			if (!object.passable()) {
				return false;
			}
		}

		return true;
	}

	public ArrayList<Tile> getAdjacent() {
		ArrayList<Tile> tiles = new ArrayList<>();
		for (Compass compass : Compass.values()) {
			tiles.add(adjacent.get(compass));
		}
		return tiles;
	}

	public void setAdjacent(Map<Compass,Tile> adjacent) {
		this.adjacent = adjacent;
	}

	public boolean isAdjacent(Tile tile){
		return adjacent.containsValue(tile);
	}
    
}