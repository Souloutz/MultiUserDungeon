package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class UseItemAction implements Action {

	private Game receiver;
	private int bagPos;
	private int itemPos;

	public UseItemAction(Game game, int bagPos, int itemPos) {
		receiver = game;
		this.bagPos = bagPos;
		this.itemPos = itemPos;
	}

	@Override
	public void execute() {
		receiver.handleUseItem(bagPos, itemPos);
	}
}
