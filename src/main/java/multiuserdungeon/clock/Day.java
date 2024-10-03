package multiuserdungeon.clock;

public class Day implements Time {

	private final Clock clock;

	public Day(Clock clock) {
		this.clock = clock;
	}

	public Clock getClock() {
		return this.clock;
	}


	@Override
	public boolean isDay() {
		return true;
	}

	@Override
	public double getStatChange(CreatureBuff buff) {
		if (buff == CreatureBuff.DIURNAL) {
			return 1 + buff.getStatChange();
		}
		return 1 - buff.getStatChange();
	}

	@Override
	public String toString() {
		int turnsToFlip = 9 - this.clock.getTurn();
		return "Day : " + turnsToFlip;
	}


}
