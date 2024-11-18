package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class BrowseMapAction implements Action<Boolean> {
    
    private final Authenticator auth;
    private final String filePath;

    public BrowseMapAction(Authenticator auth, String filePath) {
        this.auth = auth;
        this.filePath = filePath;
    }

    @Override
    public Boolean execute() {
	    if(!canExecute()) return false;
	    this.auth.getUser().handleBrowseMap(this.filePath);
        return true;
    }

    @Override
    public boolean canExecute() {
        return true;
    }

}
