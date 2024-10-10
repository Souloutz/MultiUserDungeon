package multiuserdungeon.commands.movement;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class ExitRoomAction implements Action<Boolean> {

	private final Game receiver;
	private final Compass direction;

	public ExitRoomAction(Game game, Compass direction) {
		this.receiver = game;
		this.direction = direction;
	}

	@Override
	public Boolean execute() {
		return this.receiver.handleExitRoom(this.direction);
	}

}
