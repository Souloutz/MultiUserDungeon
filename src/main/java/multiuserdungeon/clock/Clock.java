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
		if (this.turnCounter >= 9) {
			if (this.time.isDay()) {
				this.time = new Night(this);
			} else {
				this.time = new Day(this);
			}
			this.turnCounter = 0;
		} else {
			this.turnCounter ++;
		}

		this.time.handle();
		// TODO: call other turn ending logic
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

	//Testing purposes
	public static void main(String[] args) {
		Clock c = new Clock();

		System.out.println(c);



		//System.out.println("Current Time: " + c.getCurrentTime());

	}

}
