package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class ChangePasswordAction implements Action<Boolean> {

    private final Authenticator receiver;
    private final Profile profile;
    private final String username;
    private final String curPassword;
    private final String newPassword;

    public ChangePasswordAction(Authenticator authenticator, Profile profile, String username, String curPassword, String newPassword) {
        this.receiver = authenticator;
        this.profile = profile;
        this.username = username;
        this.curPassword = curPassword;
        this.newPassword = newPassword;
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