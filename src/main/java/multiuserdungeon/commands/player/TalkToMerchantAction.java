package multiuserdungeon.commands.player;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Compass;

public class TalkToMerchantAction implements Action<List<InventoryElement>> {
    
    private final Game receiver;
    private final Compass direction;

    public TalkToMerchantAction(Game game, Compass direction) {
        this.receiver = game;
        this.direction = direction;
    }

    @Override
    public List<InventoryElement> execute() {
	    if(!canExecute()) return null;
	    return this.receiver.handleTalkToMerchant(direction);
    }

    @Override
    public boolean canExecute() {
        return this.receiver != null;
    }
}
