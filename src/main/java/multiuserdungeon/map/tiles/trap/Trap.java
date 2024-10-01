package multiuserdungeon.map.tiles.trap;

import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Trap implements TileObject {

	private final int damage;
	private TrapStatus status;

	public Trap(int damage) {
		this.damage = damage;
		this.status = new UndetectedTrap(this);
	}

	public int getDamage() {
		return this.damage;
	}

	public void detected() {
		this.status.handleDetected();
	}

	public void disarmAttempt() {
		this.status.handleDisarmAttempt();
	}

	public void setStatus(TrapStatus status) {
		this.status = status;
	}

	@Override
	public String getName() {
		return "Trap";
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
		return true;
	}

	@Override
	public boolean isTrap() {
		return true;
	}

}
