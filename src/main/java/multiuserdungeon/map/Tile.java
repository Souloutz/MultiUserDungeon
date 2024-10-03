package multiuserdungeon.map;

import java.util.ArrayList;
import java.util.Map;
import multiuserdungeon.map.tiles.*;

public class Tile {

	
	private ArrayList<TileObject> objects;
	private Map<Compass,Tile> adjacent;
	public int x;
	public int y;

	public Tile(int x, int y, TileObject object, Map<Compass,Tile> adjacent) {
		this.x = x;
		this.y = y;
		this.objects = new ArrayList<TileObject>();
		this.objects.add(object);
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

	public String getObjectName() {
		return objects.get(0).getName();
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
		return null;
	}

	public boolean isAdjacent(Tile tile){
		return adjacent.containsValue(tile);
	}
    
}