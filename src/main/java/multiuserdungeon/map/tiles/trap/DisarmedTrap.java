package multiuserdungeon.map.tiles.trap;

public class DisarmedTrap implements TrapStatus {

	private final Trap trap;

	public DisarmedTrap(Trap trap) {
		this.trap = trap;
	}

	@Override
	public void handleDetected() {
		// Final state - nothing happens
	}

	@Override
	public void handleDisarmAttempt() {
		// Final state - nothing happens
	}

}
