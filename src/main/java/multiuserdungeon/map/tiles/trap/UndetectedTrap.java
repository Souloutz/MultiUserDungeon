package multiuserdungeon.map.tiles.trap;

import multiuserdungeon.Game;

public class UndetectedTrap implements TrapStatus {

	private final Trap trap;

	public UndetectedTrap(Trap trap) {
		this.trap = trap;
	}

	@Override
	public void handleDetected() {
		if(Game.getInstance().getMap().getPlayerRoom().getPlayerTile().equals(this.trap.getTile())) {
			Game.getInstance().getPlayer().attacked(this.trap.getDamage());
			this.trap.setStatus(new DisarmedTrap());
		} else if(Math.random() <= 0.5) {
			this.trap.setStatus(new DetectedTrap(this.trap));
		}
	}

	@Override
	public boolean handleDisarmAttempt() {
		// Nothing happens
		return false;
	}

	@Override
	public boolean isDetected() {
		return false;
	}

	@Override
	public boolean isDisarmed() {
		return false;
	}

}
