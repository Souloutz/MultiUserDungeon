package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class LoginAction implements Action<Boolean> {
    
    private final Authenticator receiver;
    private final String username;
    private final String password;

    public LoginAction(Authenticator authenticator, String username, String password) {
        this.receiver = authenticator;
        this.username = username;
        this.password = password;
    }

    @Override
    public Boolean execute() {
	    if (!canExecute()) return false;
	    return this.receiver.login(username, password);
    }

    @Override
    public boolean canExecute() {
        return !this.receiver.loggedIn();
    }

}
