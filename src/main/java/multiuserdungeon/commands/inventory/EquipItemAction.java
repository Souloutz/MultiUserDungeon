package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class EquipItemAction implements Action<Boolean> {

	private final Game receiver;
	private final Profile profile;
	private final int bagPos;
	private final int itemPos;

	public EquipItemAction(Game game, Profile profile, int bagPos, int itemPos) {
		this.receiver = game;
		this.profile = profile;
		this.bagPos = bagPos;
		this.itemPos = itemPos;
	}

	@Override
	public Boolean execute() {
		if (canExecute())
			return this.receiver.handleEquipItem(this.bagPos, this.itemPos);

		return false;
	}

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
