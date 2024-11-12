package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class ViewHistoryAction implements Action<String> {
    
    private final User receiver;

    public ViewHistoryAction(User user) {
        this.receiver = user;
    }

    @Override
    // TODO string or some other return type
    public String execute() {
        if (canExecute())
            return ((Profile) this.receiver).getStats().toString();

        return null;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof Profile)
			return true;

		return false;
    }
}
