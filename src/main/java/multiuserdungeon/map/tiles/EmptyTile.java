package multiuserdungeon.map.tiles;

import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class EmptyTile implements TileObject {

	public EmptyTile () {}

	private Tile tile;

	@Override
	public String getName() {
		return "Empty Tile";
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
