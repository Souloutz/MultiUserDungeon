package multiuserdungeon.clock;

public interface Time {

	void handle();

	boolean isDay();

	double getStatChange(CreatureBuff buff);

}
