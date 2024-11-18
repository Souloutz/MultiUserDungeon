package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class DestroyItemAction implements Action<Boolean> {

	private final Game receiver;
	private final int bagPos;
	private final int itemPos;

	public DestroyItemAction(Game game, int bagPos, int itemPos) {
		this.receiver = game;
		this.bagPos = bagPos;
		this.itemPos = itemPos;
	}

	@Override
	public Boolean execute() {
		if(!canExecute()) return false;
		return this.receiver.handleDestroyItem(this.bagPos, this.itemPos);
	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}

}
