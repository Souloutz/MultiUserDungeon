package multiuserdungeon.map.tiles.trap;

import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Trap implements TileObject {

	private final int damage;
	private TrapStatus status;
	private Tile tile;

	public Trap(int damage) {
		this.damage = damage;
		this.status = new UndetectedTrap(this);
		this.tile = null;
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

	public boolean isDetected() {
		return this.status.isDetected();
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

}
