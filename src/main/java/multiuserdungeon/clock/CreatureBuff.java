package multiuserdungeon.clock;

public enum CreatureBuff {

	DIURNAL(0.1),
	NOCTURNAL(0.2);

	private final double buff;

	CreatureBuff(double buff) {
		this.buff = buff;
	}

	public double getStatChange() {
		return this.buff;
	}

}
