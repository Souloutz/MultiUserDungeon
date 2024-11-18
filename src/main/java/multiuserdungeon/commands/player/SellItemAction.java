package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class SellItemAction implements Action<Boolean> {
    
    private final Game receiver;
    private final Compass direction;
    private final int bagPos;
    private final int itemPos;

    public SellItemAction(Game game, Compass direction, int bagPos, int itemPos) {
        this.receiver = game;
        this.direction = direction;
        this.bagPos = bagPos;
        this.itemPos = itemPos;
    }

    @Override
    public Boolean execute() {
	    if(!canExecute()) return false;
	    return this.receiver.handleSellItem(this.direction, this.bagPos, this.itemPos);
    }

    @Override
    public boolean canExecute() {
        return this.receiver != null;
    }
}
