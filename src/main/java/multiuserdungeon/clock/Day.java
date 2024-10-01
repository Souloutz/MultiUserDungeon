package multiuserdungeon.clock;

public class Day implements Time {

	public Day(Clock clock) {

	}

	@Override
	public void handle() {

	}

	@Override
	public boolean isDay() {
		return true;
	}

	@Override
	public double getStatChange(CreatureBuff buff) {
		return 0;
	}

}
