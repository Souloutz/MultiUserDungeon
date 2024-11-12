package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class ViewHistoryAction implements Action<String> {
    
    private final Profile receiver;

    public ViewHistoryAction(Profile profile) {
        this.receiver = profile;
    }

    @Override
    public String execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean canExecute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
    }
}
