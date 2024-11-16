package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class UseItemAction implements Action<Boolean> {

	private final Game receiver;
	private final User user;
	private final int bagPos;
	private final int itemPos;

	public UseItemAction(Game game, User user, int bagPos, int itemPos) {
		this.receiver = game;
		this.user = user;
		this.bagPos = bagPos;
		this.itemPos = itemPos;
	}

	@Override
	public Boolean execute() {
		if (canExecute())
			return this.receiver.handleUseItem(this.bagPos, this.itemPos);

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
