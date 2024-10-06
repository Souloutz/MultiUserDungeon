package multiuserdungeon.clock;

public class Clock {

	private int turnCounter;
	private Time time;

	public Clock() {
		this.turnCounter = 0;
		this.time = new Day(this);
	}

	public Time getCurrentTime() {
		return this.time;
	}

	public void completeTurn() {
		this.time.handle();
	}

	public void incrementTurn() {
		this.turnCounter ++;
	}

	public void resetTurn() {
		this.turnCounter = 0;
	}

	public void setCurrentTime(Time time) {
		this.time = time;
	}

	public int getTurn() {
		return this.turnCounter;
	}

	public boolean isDay() {
		return this.time.isDay();
	}

	@Override
	public String toString() {
		return this.time.toString();
	}

}
