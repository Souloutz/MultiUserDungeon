package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class ViewInventoryAction implements Action<String> {
    
    private final Game receiver;
    private final User user;

    public ViewInventoryAction(Game game, User user) {
        this.receiver = game;
        this.user = user; 
    }

    @Override
    public String execute() {
        if (canExecute())
            return this.receiver.handleViewInventory();
        
        return null;
    }

    @Override
    public boolean canExecute() {
        //TODO{Make very specific to determine if this action should be an option}
        if (user instanceof Profile)
			return true;

		return false;
    }
}
