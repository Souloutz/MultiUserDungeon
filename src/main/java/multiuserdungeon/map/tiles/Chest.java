package multiuserdungeon.map.tiles;

import java.util.ArrayList;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Chest implements TileObject {

	private String name;
	private Tile tile;
	private ArrayList<InventoryElement> items;

	public Chest(String name, ArrayList<InventoryElement> items) {
		this.name = name;
		this.items = items;
	}

	public ArrayList<InventoryElement> handleSearch() {
		return items;
	}

	public InventoryElement handleLoot(int itemPos) {
		return items.remove(itemPos);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Tile getTile() {
		return tile;
	}

	@Override
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	@Override
	public boolean passable() {
		return true;
	}

	@Override
	public boolean isTrap() {
		return false;
	}

}
