package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class SwapBagAction implements Action<Boolean> {

	private final Game receiver;
	private final int sourceBagPos;
	private final int destBagPos;
	private final int destItemPos;

	public SwapBagAction(Game game, int sourceBagPos, int destBagPos, int destItemPos) {
		this.receiver = game;
		this.sourceBagPos = sourceBagPos;
		this.destBagPos = destBagPos;
		this.destItemPos = destItemPos;
	}

	@Override
	public Boolean execute() {
		if(!canExecute()) return false;
		return this.receiver.handleSwapBag(this.sourceBagPos, this.destBagPos, this.destItemPos);
	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}

}
