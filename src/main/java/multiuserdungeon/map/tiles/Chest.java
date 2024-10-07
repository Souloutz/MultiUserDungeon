package multiuserdungeon.map.tiles;

import java.util.ArrayList;
import java.util.List;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Chest implements TileObject {

	private final String name;
	private final List<InventoryElement> contents;
	private Tile tile;

	public Chest(String name, ArrayList<InventoryElement> contents) {
		this.name = name;
		this.contents = contents;
		this.tile = null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Tile getTile() {
		return this.tile;
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
	public char getASCII() {
		return 'C';
	}

	public List<InventoryElement> getContents() {
		return this.contents;
	}

	public InventoryElement handleLoot(int index) {
		if(index >= this.contents.size()) return null;
		return this.contents.remove(index);
	}

	@Override
	public String toString() {
		return "A " + this.name + " containing " + this.contents.size() + " items.";
	}

}
