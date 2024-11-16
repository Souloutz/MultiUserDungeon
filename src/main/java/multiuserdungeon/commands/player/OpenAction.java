package multiuserdungeon.commands.player;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class OpenAction implements Action<List<InventoryElement>> {

	private final Game receiver;
	private final User user;

	public OpenAction(Game game, User user) {
		this.receiver = game;
		this.user = user;
	}

	@Override
	public List<InventoryElement> execute() {
		if (canExecute())
			return this.receiver.handleOpen();

		return null;
	}

	@Override
	public boolean canExecute() {
		//TODO{Make very specific to determine if this action should be an option}
		if (user instanceof Profile)
			return true;

		return false;
	}
}
