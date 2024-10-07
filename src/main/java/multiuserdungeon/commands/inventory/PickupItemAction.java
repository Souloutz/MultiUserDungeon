package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class PickupItemAction implements Action<Boolean> {

	private final Game receiver;
	private final int index;

	public PickupItemAction(Game game, int index) {
		this.receiver = game;
		this.index = index;
	}

	@Override
	public Boolean execute() {
		return this.receiver.handlePickupItem(this.index);
	}

}
