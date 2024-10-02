package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class DisarmTrapAction implements Action {

	private Game receiver;
	private Compass direction;

	public DisarmTrapAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public void execute() {
		receiver.handleDisarmTrap(direction);
	}
}
