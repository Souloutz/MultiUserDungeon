package multiuserdungeon.map.tiles.trap;

import multiuserdungeon.Game;

public class DetectedTrap implements TrapStatus {

	private final Trap trap;

	public DetectedTrap(Trap trap) {
		this.trap = trap;
	}

	@Override
	public void handleDetected() {
		// Nothing happens
	}

	@Override
	public boolean handleDisarmAttempt() {
		boolean result = false;
		if(Math.random() <= 0.5) {
			Game.getInstance().getPlayer().attacked(this.trap.getDamage());
		} else {
			result = true;
		}

		this.trap.setStatus(new DisarmedTrap());
		return result;
	}

	@Override
	public boolean isDetected() {
		return true;
	}

	@Override
	public boolean isDisarmed() {
		return false;
	}

}
