package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class SwapBagAction implements Action<Void> {

	private Game receiver;
	private int sourceBagPos;
	private int destBagPos;

	public SwapBagAction(Game game, int sourceBagPos, int destBagPos) {
		receiver = game;
		this.sourceBagPos = sourceBagPos;
		this.destBagPos = destBagPos;
	}

	@Override
	public Void execute() {
		receiver.handleSwapBag(sourceBagPos, destBagPos);
		return null;
	}
}
