package multiuserdungeon.commands.movement;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class MoveAction implements Action<Void> {

	private Game receiver;
	private Compass direction;

	public MoveAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public Void execute() {
		receiver.handleMove(direction);
		return null;
	}
}
