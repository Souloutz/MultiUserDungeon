package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class SwapBagAction implements Action<Void> {

	private Game receiver;
	private int sourceBagPos;
	private int destBagPos;
	private int destItemPos;

	public SwapBagAction(Game game, int sourceBagPos, int destBagPos, int destItemPos) {
		receiver = game;
		this.sourceBagPos = sourceBagPos;
		this.destBagPos = destBagPos;
		this.destItemPos = destItemPos;
	}

	@Override
	public Void execute() {
		receiver.handleSwapBag(sourceBagPos, destBagPos, destItemPos);
		return null;
	}
}
