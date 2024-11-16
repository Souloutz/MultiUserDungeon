package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class PickupItemAction implements Action<Boolean> {

	private final Game receiver;
	private final User user;
	private final int index;

	public PickupItemAction(Game game, User user, int index) {
		this.receiver = game;
		this.user = user;
		this.index = index;
	}

	@Override
	public Boolean execute() {
		if (canExecute())
			return this.receiver.handlePickupItem(this.index);

		return false;
	}

	@Override
	public boolean canExecute() {
		//TODO{Make very specific to determine if this action should be an option}
		if (user instanceof Profile)
			return true;

		return false;
	}
}
