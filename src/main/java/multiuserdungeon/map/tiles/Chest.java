package multiuserdungeon.map.tiles;

import java.util.List;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Chest implements TileObject {

	List<InventoryElement> contents; // TODO

	public Chest(int size) {

	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Tile getTile() {
		return null;
	}

	@Override
	public void setTile(Tile tile) {

	}

	@Override
	public boolean passable() {
		return false;
	}

	@Override
	public boolean isTrap() {
		return false;
	}
	
	public List<InventoryElement> loot() {
		return null; // TODO
	}

	public List<InventoryElement> getContents() {
		return contents; 
	}
}
