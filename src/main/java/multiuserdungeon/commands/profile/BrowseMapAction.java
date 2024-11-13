package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class BrowseMapAction implements Action<Void> {
    
    private final User receiver;
    private final String filePath;

    public BrowseMapAction(User user, String filePath) {
        this.receiver = user;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        if (canExecute())
            this.receiver.handleBrowseMap(filePath);

        return null;
    }

    @Override
    public boolean canExecute() {
        return true;
    }
}
