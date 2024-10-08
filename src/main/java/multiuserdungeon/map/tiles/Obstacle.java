package multiuserdungeon.map.tiles;

import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Obstacle implements TileObject {

	private final String name;
	private Tile tile;

	public Obstacle(String name) {
		this.name = name;
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
		return false;
	}

	@Override
	public char getASCII() {
		return 'O';
	}

	@Override
	public String toString() {
		return "A(n) " + this.name;
	}

}
