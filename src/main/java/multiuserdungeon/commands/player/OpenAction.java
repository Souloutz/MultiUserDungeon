package multiuserdungeon.commands.player;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class OpenAction implements Action<List<InventoryElement>> {

	private final Game receiver;
	private final Profile profile;

	public OpenAction(Game game, Profile profile) {
		this.receiver = game;
		this.profile = profile;
	}

	@Override
	public List<InventoryElement> execute() {
		if (canExecute())
			return this.receiver.handleOpenChest();

		return null;
	}

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
