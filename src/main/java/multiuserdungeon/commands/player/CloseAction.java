package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class CloseAction implements Action<Void> {
    
    private final Game receiver;
    private final User user;

    public CloseAction(Game game, User user) {
        this.receiver = game;
        this.user = user;
    }

    @Override
    public Void execute() {
        if (canExecute()) {
            this.receiver.handleClose();
        }

        return null;
    }

    @Override
    public boolean canExecute() {
        if (user instanceof Profile)
			return true;

		return false;
    }
}
