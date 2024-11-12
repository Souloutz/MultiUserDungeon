package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class PickupItemAction implements Action<Boolean> {

	private final Game receiver;
	private final Profile profile;
	private final int index;

	public PickupItemAction(Game game, Profile profile, int index) {
		this.receiver = game;
		this.profile = profile;
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
