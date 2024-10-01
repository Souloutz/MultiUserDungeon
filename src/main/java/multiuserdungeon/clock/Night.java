package multiuserdungeon.clock;

public class Night implements Time {

	private Clock clock;

	public Night(Clock clock) {
		this.clock = clock;
	}

	public Clock getClock() {
		return this.clock;
	}

	@Override
	public void handle() {

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
		int turnsToFlip = 9 - this.clock.getTurn();
		return "Night : " + turnsToFlip;
	}

}
