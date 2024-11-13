package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class SellItemAction implements Action<Boolean> {
    
    private final Game receiver;
    private final User user;
    private final int bagPos;
    private final int itemPos;

    public SellItemAction(Game game, User user, int bagPos, int itemPos) {
        this.receiver = game;
        this.user = user;
        this.bagPos = bagPos;
        this.itemPos = itemPos;
    }

    @Override
    public Boolean execute() {
        if (canExecute())
            return this.receiver.handleSellItem(bagPos, itemPos);

        return false;
    }

    @Override
    public boolean canExecute() {
        if (user instanceof Profile)
			return true;

		return false;
    }
}
