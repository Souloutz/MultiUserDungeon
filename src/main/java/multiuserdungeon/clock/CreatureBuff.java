package multiuserdungeon.clock;

public enum CreatureBuff {

	NOCTURNAL(0.20),
	DIURNAL(0.10);

	CreatureBuff(double statChange) {

	}

	public double getStatChange() {
		return 0;
	}

}
