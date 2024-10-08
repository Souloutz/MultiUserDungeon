package multiuserdungeon.clock;

public class Night implements Time {

	private final Clock clock;

	public Night(Clock clock) {
		this.clock = clock;
	}

	public Clock getClock() {
		return this.clock;
	}

	@Override
	public void handle() {
		if(this.clock.getTurn() >= 9) {
			this.clock.setCurrentTime(new Day(this.clock));
			this.clock.resetTurn();
		} else {
			this.clock.incrementTurn();
		}
	}

	@Override
	public boolean isDay() {
		return false;
	}

	@Override
	public double getStatChange(CreatureBuff buff) {
		if (buff == CreatureBuff.NOCTURNAL) {
			return 1 + buff.getStatChange();
		}
		return 1 - buff.getStatChange();
	}

	@Override
	public String toString() {
		int turnsToFlip = 10 - this.clock.getTurn();
		return "Night (" + turnsToFlip + " more turns)";
	}

}
