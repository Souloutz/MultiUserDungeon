package multiuserdungeon.map.tiles;

import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class EmptyTile implements TileObject {

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Tile getTile() {
		return null;
	}

	@Override
	public boolean passable() {
		return false;
	}

	@Override
	public boolean isTrap() {
		return false;
	}

}
