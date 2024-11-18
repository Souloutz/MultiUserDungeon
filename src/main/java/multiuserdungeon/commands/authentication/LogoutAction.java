package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class LogoutAction implements Action<Boolean> {
    
    private final Authenticator receiver;

    public LogoutAction(Authenticator auth) {
        this.receiver = auth;
    }

    @Override
    public Boolean execute() {
	    if(!canExecute()) return false;
	    this.receiver.logout();
        return true;
    }

    @Override
    public boolean canExecute() {
        return this.receiver.loggedIn();
    }

}
