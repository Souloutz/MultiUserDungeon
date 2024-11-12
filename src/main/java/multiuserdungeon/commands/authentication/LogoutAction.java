package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class LogoutAction implements Action<Boolean> {
    
    private final Authenticator receiver;
    private final Profile profile;

    public LogoutAction(Authenticator authenticator, Profile profile) {
        this.receiver = authenticator;
        this.profile = profile;
    }

    @Override
    public Boolean execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean canExecute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
    }    
}
