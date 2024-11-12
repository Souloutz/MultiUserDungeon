package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class StartGameAction implements Action<Void> {
    
    private final User receiver;

    public StartGameAction(User user) {
        this.receiver = user;
    }

    @Override
    public Void execute() {
        if (canExecute())
            ((Profile) this.receiver).handleNewGame();

        return null;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof Profile)
			return true;

		return false;
    }
}
