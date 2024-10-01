package multiuserdungeon.clock;

public class Night implements Time {

	public Night(Clock clock) {

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
		return 0;
	}

}
