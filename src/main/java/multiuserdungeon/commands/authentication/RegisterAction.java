package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class RegisterAction implements Action<Boolean> {
    
    private final Authenticator receiver;
    private final String username;
    private final String description;
    private final String password;
    private final String confirmPassword;
    
    public RegisterAction(Authenticator auth, String username, String description, String password, String confirmPassword) {
        this.receiver = auth;
        this.username = username;
        this.description = description;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    @Override
    public Boolean execute() {
	    if(!canExecute()) return false;
	    return this.receiver.register(this.username, this.description, this.password, this.confirmPassword);
    }

    @Override
    public boolean canExecute() {
        return !this.receiver.loggedIn();
    }

}
