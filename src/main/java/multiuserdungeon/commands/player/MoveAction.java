package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class MoveAction implements Action<Boolean> {

	private final Game receiver;
	private final Compass direction;

	public MoveAction(Game game, Compass direction) {
		this.receiver = game;
		this.direction = direction;
	}

	@Override
	public Boolean execute() {
		if(!canExecute()) return false;
		return this.receiver.handleMove(this.direction);
	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}

}
