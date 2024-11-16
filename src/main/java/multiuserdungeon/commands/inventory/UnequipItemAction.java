package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class UnequipItemAction implements Action<Boolean> {

	private final Game receiver;
	private final User user;
	private final boolean isWeapon;

	public UnequipItemAction(Game game, User user, boolean isWeapon) {
		this.receiver = game;
		this.user = user;
		this.isWeapon = isWeapon;
	}

	@Override
	public Boolean execute() {
		if (canExecute())
			return this.receiver.handleUnequipItem(this.isWeapon);

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
