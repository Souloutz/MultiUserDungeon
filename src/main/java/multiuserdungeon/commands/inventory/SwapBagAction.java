package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class SwapBagAction implements Action {

	Game receiver;
	int sourceBagPos;
	int destBagPos;

	public SwapBagAction(Game game, int sourceBagPos, int destBagPos) {
		receiver = game;
		this.sourceBagPos = sourceBagPos;
		this.destBagPos = destBagPos;
	}

	@Override
	public void execute() {
		receiver.handleSwapBag(sourceBagPos, destBagPos);
	}
}
