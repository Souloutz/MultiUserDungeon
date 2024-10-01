package multiuserdungeon.map.tiles.trap;

import multiuserdungeon.Game;
import multiuserdungeon.map.Compass;

public class UndetectedTrap implements TrapStatus {

	private final Trap trap;

	public UndetectedTrap(Trap trap) {
		this.trap = trap;
	}

	@Override
	public void handleDetected() {
		if(Game.getInstance().getMap().getPlayerRoom().getPlayerTile().equals(this.trap.getTile())) {
			Game.getInstance().getPlayer().attacked(Compass.NORTH, this.trap.getDamage());
			this.trap.setStatus(new DisarmedTrap());
		} else if(Math.random() <= 0.5) {
			this.trap.setStatus(new DetectedTrap(this.trap));
		}
	}

	@Override
	public void handleDisarmAttempt() {
		// Nothing happens
	}

	@Override
	public boolean isDetected() {
		return false;
	}


}
