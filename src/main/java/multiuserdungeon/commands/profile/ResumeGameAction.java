package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class ResumeGameAction implements Action<Void> {
    
    private final User receiver;
    private final String filename;

    public ResumeGameAction(User user, String filename) {
        this.receiver = user;
        this.filename = filename;
    }

    @Override
    public Void execute() {
        if (canExecute())
            ((Profile) this.receiver).handleResumeGame(filename);

        return null;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof Profile)
			return true;

		return false;
    }
}
