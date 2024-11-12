package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class ChangePasswordAction implements Action<Boolean> {

    private final Authenticator receiver;
    private final String curPassword;
    private final String newPassword;

    public ChangePasswordAction(Authenticator authenticator, String curPassword, String newPassword) {
        this.receiver = authenticator;
        this.curPassword = curPassword;
        this.newPassword = newPassword;
    }

    @Override
    public Boolean execute() {
        if (canExecute())
           return this.receiver.handleChangePassword(curPassword, newPassword);

        return false;
    }

    @Override
    public boolean canExecute() {
        // checking handled in authenticator class
        return true;
    }  
}