package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class LogoutAction implements Action<Boolean> {
    
    private final Authenticator receiver;

    public LogoutAction(Authenticator authenticator) {
        this.receiver = authenticator;
    }

    @Override
    public Boolean execute() {
        if (canExecute())
            return this.receiver.logout();

        return false;
    }

    @Override
    public boolean canExecute() {
        // checking handled in authenticator class
        return true;
    }    
}
