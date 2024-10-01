package multiuserdungeon.map.tiles.trap;

import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Trap implements TileObject {

	public Trap(int damage) {

	}

	public int getDamage() {
		return 0;
	}

	public void detected() {

	}

	public void disarmAttempt() {

	}

	public void setStatus(TrapStatus status) {

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
	public boolean passable() {
		return false;
	}

	@Override
	public boolean isTrap() {
		return false;
	}

}
