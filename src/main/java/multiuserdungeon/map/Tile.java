package multiuserdungeon.map;

import java.util.Collection;

public class Tile {

	public Tile(int x, int y, TileObject object) {

	}

	public int getX() {
		return 0; // TODO
	}

	public int getY() {
		return 0; // TODO
	}

	public TileObject getObject() {
		return null;
	}

	public void setObject(TileObject object) {

	}

	public String getObjectName() {
		return "";
	}

	public boolean hasTrap() {
		return false;
	}

	public boolean hasCharacter() {
		return false;
	}

	public boolean hasChest() {
		return false;
	}

	public boolean passable() {
		return false;
	}

	public Collection<Tile> getAdjacent() {
		return null;
	}
    
}
