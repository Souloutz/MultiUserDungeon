package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class ViewInventoryAction implements Action<String> {

    Game receiver;

    public ViewInventoryAction(Game game) {
        receiver = game;
    }

    @Override
    public String execute() {
        return receiver.handleViewInventory();
    }    
}
