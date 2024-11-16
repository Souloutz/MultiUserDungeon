package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class AttackAction implements Action<Integer> {

	private final Game receiver;
	private final User user;
	private final Compass direction;

	public AttackAction(Game game, User user, Compass direction) {
		this.receiver = game;
		this.user = user;
		this.direction = direction;
	}

	@Override
	public Integer execute() {
		if (canExecute())
			return this.receiver.handleAttack(this.direction);

		return -1;
	}

	@Override
	public boolean canExecute() {
		//TODO{Make very specific to determine if this action should be an option}
		if (user instanceof Profile)
			return true;

		return false;
	}
}
