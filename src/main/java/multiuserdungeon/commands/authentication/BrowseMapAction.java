package multiuserdungeon.commands.authentication;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class BrowseMapAction implements Action<Game> {
    
    private final Authenticator auth;
    private final String filePath;

    public BrowseMapAction(Authenticator auth, String filePath) {
        this.auth = auth;
        this.filePath = filePath;
    }

    @Override
    public Game execute() {
	    if(!canExecute()) return null;
	    return this.auth.getUser().handleBrowseMap(this.filePath);
    }

    @Override
    public boolean canExecute() {
        return true;
    }

}
