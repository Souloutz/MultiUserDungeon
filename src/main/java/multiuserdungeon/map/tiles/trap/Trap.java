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

	//copy constructor
	public Trap(Trap trap) {
		this.damage = trap.getDamage();
		if(trap.status.isDetected()){
			this.status = new DetectedTrap(this);
		}
		else if(trap.status.isDisarmed()){
			this.status = new DisarmedTrap();
		}
		else{
			this.status = new UndetectedTrap(this);
		}
		this.tile = null;
		
	}

	public int getDamage() {
		return this.damage;
	}

	public void setStatus(TrapStatus status) {
		this.status = status;
	}

	public void detected() {
		this.status.handleDetected();
	}

	public boolean disarmAttempt() {
		return this.status.handleDisarmAttempt();
	}

	public boolean isDetected() {
		return this.status.isDetected();
	}

	public boolean isDisarmed() {
		return this.status.isDisarmed();
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

	@Override
	public char getASCII() {
		return isDisarmed() ? '$' : isDetected() ? '!' : '-';
	}

	@Override
	public String toString() {
		String type;
		if(isDetected() && isDisarmed()) {
			type = "A disarmed trap";
		} else if(isDetected()) {
			type = "A detected trap";
		} else {
			type = "An undetected trap";
		}

		return type + " (" + this.damage + " damage)";
	}

}
