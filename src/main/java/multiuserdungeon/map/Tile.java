package multiuserdungeon.map;

public class Tile {

	public Tile(int x, int y) {

	}

	public TileObject getObject() {
		return null;
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
    
}
