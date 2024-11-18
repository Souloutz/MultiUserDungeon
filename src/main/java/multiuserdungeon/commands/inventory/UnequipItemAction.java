package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class UnequipItemAction implements Action<Boolean> {

	private final Game receiver;
	private final boolean isWeapon;

	public UnequipItemAction(Game game, boolean isWeapon) {
		this.receiver = game;
		this.isWeapon = isWeapon;
	}

	@Override
	public Boolean execute() {
		if(!canExecute()) return false;
		return this.receiver.handleUnequipItem(this.isWeapon);

	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}

}
