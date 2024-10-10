package multiuserdungeon.map.tiles.trap;

public interface TrapStatus {

	void handleDetected();

	boolean handleDisarmAttempt();

	boolean isDetected();

	boolean isDisarmed();

}
