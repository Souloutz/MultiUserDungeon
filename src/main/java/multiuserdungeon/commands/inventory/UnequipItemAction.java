package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class UnequipItemAction implements Action<Boolean> {

	private final Game receiver;
	private final Profile profile;
	private final boolean isWeapon;

	public UnequipItemAction(Game game, Profile profile, boolean isWeapon) {
		this.receiver = game;
		this.profile = profile;
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
