package multiuserdungeon.commands.movement;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class ExitRoomAction implements Action<Void> {

	private Game receiver;
	private Compass direction;

	public ExitRoomAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public Void execute() {
		receiver.handleExitRoom(direction);
		return null;
	}
}
