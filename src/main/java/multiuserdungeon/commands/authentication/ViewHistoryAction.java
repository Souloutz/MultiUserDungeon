package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class ViewHistoryAction implements Action<String> {

    private final Authenticator auth;

    public ViewHistoryAction(Authenticator auth) {
        this.auth = auth;
    }

    @Override
    public String execute() {
	    if(!canExecute()) return null;
	    return ((Profile) this.auth.getUser()).viewHistory();
    }

    @Override
    public boolean canExecute() {
        return this.auth.loggedIn();
    }

}
