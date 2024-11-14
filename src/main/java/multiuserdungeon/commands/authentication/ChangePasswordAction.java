package multiuserdungeon.commands.authentication;

import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.commands.Action;

public class ChangePasswordAction implements Action<Boolean> {

    private final Authenticator receiver;
    private final String curPassword;
    private final String newPassword;
    private final String confirmPassword;

    public ChangePasswordAction(Authenticator authenticator, String curPassword, String newPassword, String confirmPassword) {
        this.receiver = authenticator;
        this.curPassword = curPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    @Override
    public Boolean execute() {
        if (canExecute())
           return this.receiver.handleChangePassword(curPassword, newPassword, confirmPassword);

        return false;
    }

    @Override
    public boolean canExecute() {
        // checking handled in authenticator class
        return true;
    }  
}