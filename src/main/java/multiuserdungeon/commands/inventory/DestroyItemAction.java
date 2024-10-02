package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class DestroyItemAction implements Action {

	private Game receiver;
	private int bagPos;
	private int itemPos;

	public DestroyItemAction(Game game, int bagPos, int itemPos) {
		receiver = game;
		this.bagPos = bagPos;
		this.itemPos = bagPos;
	}

	@Override
	public void execute() {
		receiver.handleDestroyItem(bagPos, itemPos);
	}
}
