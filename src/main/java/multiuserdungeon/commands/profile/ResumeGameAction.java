package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

// TODO remove?? loadmap is resumegame?
public class ResumeGameAction implements Action<Void> {
    
    private final User receiver;
    private final String filePath;

    public ResumeGameAction(User user, String filePath) {
        this.receiver = user;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        if (canExecute())
            ((Profile) this.receiver).handleResumeGame(filePath);

        return null;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof Profile)
			return true;

		return false;
    }
}
