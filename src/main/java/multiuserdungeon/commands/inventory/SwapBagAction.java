package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class SwapBagAction implements Action<Boolean> {

	private final Game receiver;
	private final Profile profile;
	private final int sourceBagPos;
	private final int destBagPos;
	private final int destItemPos;

	public SwapBagAction(Game game, Profile profile, int sourceBagPos, int destBagPos, int destItemPos) {
		this.receiver = game;
		this.profile = profile;
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
