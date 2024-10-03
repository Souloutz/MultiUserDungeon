package multiuserdungeon.map.tiles.trap;

import multiuserdungeon.Game;
import multiuserdungeon.map.Compass;

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
	public void handleDisarmAttempt() {
		if(Math.random() <= 0.5) {
			Game.getInstance().getPlayer().attacked(Compass.NORTH, this.trap.getDamage());
		}

		this.trap.setStatus(new DisarmedTrap());
	}

	@Override
	public boolean isDetected() {
		return true;
	}


}
