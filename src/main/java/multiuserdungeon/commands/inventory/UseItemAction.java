package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class UseItemAction implements Action<Boolean> {

	private final Game receiver;
	private final int bagPos;
	private final int itemPos;

	public UseItemAction(Game game, int bagPos, int itemPos) {
		receiver = game;
		this.bagPos = bagPos;
		this.itemPos = itemPos;
	}

	@Override
	public Boolean execute() {
		return this.receiver.handleUseItem(this.bagPos, this.itemPos);
	}

}
