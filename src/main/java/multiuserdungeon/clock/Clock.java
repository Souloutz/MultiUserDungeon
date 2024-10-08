package multiuserdungeon.clock;

public class Clock {

	private int turnCounter;
	private Time time;

	public Clock() {
		this(0);
	}

	public Clock(int turnCounter) {
		this.turnCounter = turnCounter;
		this.time = new Day(this);
	}

	public int getTurn() {
		return this.turnCounter;
	}

	public void incrementTurn() {
		this.turnCounter++;
	}

	public void resetTurn() {
		this.turnCounter = 0;
	}

	public void completeTurn() {
		this.time.handle();
	}

	public Time getCurrentTime() {
		return this.time;
	}

	public void setCurrentTime(Time time) {
		this.time = time;
	}

	public boolean isDay() {
		return this.time.isDay();
	}

	@Override
	public String toString() {
		return this.time.toString();
	}

}
