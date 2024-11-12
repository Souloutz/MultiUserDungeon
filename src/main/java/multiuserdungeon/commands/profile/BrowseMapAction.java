package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class BrowseMapAction implements Action<Void> {
    
    private final User receiver;

    public BrowseMapAction(User user) {
        this.receiver = user;
    }

    @Override
    public Void execute() {
        if (canExecute())
            this.receiver.handleBrowseMap();

        return null;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof User)
			return true;

		return false;
    }
}
