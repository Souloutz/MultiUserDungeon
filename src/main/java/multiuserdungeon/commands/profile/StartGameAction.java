package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class StartGameAction implements Action<Void> {
    
    private final User receiver;
    private final String mapType;
    private final String filePath;

    public StartGameAction(User user, String mapType, String filePath) {
        this.receiver = user;
        this.mapType = mapType;
        this.filePath = filePath;
    }

    @Override
    public Void execute() {
        if (canExecute())
            ((Profile) this.receiver).handleNewGame(mapType, filePath);

        return null;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof Profile)
			return true;

		return false;
    }
}
