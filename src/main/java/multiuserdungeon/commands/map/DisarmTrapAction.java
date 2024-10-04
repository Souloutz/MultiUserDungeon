package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.commands.responses.DisarmTrapResponse;
import multiuserdungeon.map.Compass;

public class DisarmTrapAction implements Action<Void> {

	private Game receiver;
	private Compass direction;

	public DisarmTrapAction(Game game, Compass direction) {
		receiver = game;
		this.direction = direction;
	}

	@Override
	public Void execute() {
		receiver.handleDisarmTrap(direction);
		return null;
		// TODO if success return success, if not, return fail
		// return DisarmTrapResponse.SUCCESS;
	}
}
