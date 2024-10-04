package multiuserdungeon.inventory;

public enum BuffStat {
	HEALTH,
	DEFENSE,
	ATTACK;

	public String toString() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}


