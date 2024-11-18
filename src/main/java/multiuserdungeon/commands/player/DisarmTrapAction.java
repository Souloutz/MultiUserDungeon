package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class DisarmTrapAction implements Action<Boolean> {

	private final Game receiver;
	private final Compass direction;

	public DisarmTrapAction(Game game, Compass direction) {
		this.receiver = game;
		this.direction = direction;
	}

	@Override
	public Boolean execute() {
		if(!canExecute()) return false;
		return this.receiver.handleDisarmTrap(this.direction);
	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}
}
