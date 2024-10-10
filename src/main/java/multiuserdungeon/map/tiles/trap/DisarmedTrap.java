package multiuserdungeon.map.tiles.trap;

public class DisarmedTrap implements TrapStatus {

	public DisarmedTrap() {

	}

	@Override
	public void handleDetected() {
		// Final state - nothing happens
	}

	@Override
	public boolean handleDisarmAttempt() {
		// Final state - nothing happens
		return false;
	}

	@Override
	public boolean isDetected() {
		return true;
	}

	@Override
	public boolean isDisarmed() {
		return true;
	}

}
