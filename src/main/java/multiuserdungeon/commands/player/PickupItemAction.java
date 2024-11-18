package multiuserdungeon.commands.player;

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
		if(!canExecute()) return false;
		return this.receiver.handlePickupItem(this.index);
	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}
}
