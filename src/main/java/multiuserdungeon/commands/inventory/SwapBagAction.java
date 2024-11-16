package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class SwapBagAction implements Action<Boolean> {

	private final Game receiver;
	private final User user;
	private final int sourceBagPos;
	private final int destBagPos;
	private final int destItemPos;

	public SwapBagAction(Game game, User user, int sourceBagPos, int destBagPos, int destItemPos) {
		this.receiver = game;
		this.user = user;
		this.sourceBagPos = sourceBagPos;
		this.destBagPos = destBagPos;
		this.destItemPos = destItemPos;
	}

	@Override
	public Boolean execute() {
		if (canExecute())
			return this.receiver.handleSwapBag(this.sourceBagPos, this.destBagPos, this.destItemPos);

		return false;
	}

	@Override
	public boolean canExecute() {
		//TODO{Make very specific to determine if this action should be an option}
		if (user instanceof Profile)
			return true;

		return false;
	}
}
