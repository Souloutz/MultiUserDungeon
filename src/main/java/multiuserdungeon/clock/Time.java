package multiuserdungeon.clock;

public interface Time {

	default void handle(Clock clock) {
		if (clock.getTurn() >= 9) {
			if (clock.isDay()) {
				clock.setCurrentTime(new Night(clock));
			} else {
				clock.setCurrentTime(new Day(clock));
			}
			clock.resetTurn();
		} else {
			clock.incrementTurn();
		}
	}

	boolean isDay();

	double getStatChange(CreatureBuff buff);

}
