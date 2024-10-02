package multiuserdungeon.commands.movement;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class ExitRoomAction implements Action {

	private Game receiver;
	private Compass direction;

	public ExitRoomAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public void execute() {
		receiver.handleExitRoom(direction);
	}
}
