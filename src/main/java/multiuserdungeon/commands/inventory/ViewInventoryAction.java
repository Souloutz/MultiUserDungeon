package multiuserdungeon.commands.inventory;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class ViewInventoryAction implements Action<List<InventoryElement>> {
    
    private final Game receiver;
    private final User user;

    public ViewInventoryAction(Game game, User user) {
        this.receiver = game;
        this.user = user; 
    }

    @Override
    public List<InventoryElement> execute() {
        if (canExecute())
            return this.receiver.handleViewInventory();
        
        return null;
    }

    @Override
    public boolean canExecute() {
        if (user instanceof Profile)
			return true;

		return false;
    }
}
