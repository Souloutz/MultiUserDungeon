package multiuserdungeon.map.tiles.trap;

public interface TrapStatus {

	void handleDetected();

	void handleDisarmAttempt();

	boolean isDetected();

}
