package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class ViewInventoryAction implements Action<String> {
    
    private final Game receiver;

    public ViewInventoryAction(Game game) {
        this.receiver = game;
    }

    @Override
    public String execute() {
	    if(!canExecute()) return null;
	    return this.receiver.handleViewInventory();

    }

    @Override
    public boolean canExecute() {
        return this.receiver != null;
    }

}
