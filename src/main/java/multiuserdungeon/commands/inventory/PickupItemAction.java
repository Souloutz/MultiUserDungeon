package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class PickupItemAction implements Action<Void> {

	private Game receiver;
	private int index;

	public PickupItemAction(Game game, int index) {
		receiver = game;
		this.index = index;
	}

	@Override
	public Void execute() {
		receiver.handlePickupItem(index);
		return null;
	}
}
