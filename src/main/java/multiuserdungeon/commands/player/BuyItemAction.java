package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class BuyItemAction implements Action<Boolean> {
    
    private final Game receiver;
	private final Compass direction;
    private final int index;

    public BuyItemAction(Game game, Compass direction, int index) {
        this.receiver = game;
		this.direction = direction;
        this.index = index;
    }

    @Override
    public Boolean execute() {
	    if(!canExecute()) return false;
	    return this.receiver.handleBuyItem(this.direction, this.index);
    }

    @Override
    public boolean canExecute() {
        return this.receiver != null;
    }
}
