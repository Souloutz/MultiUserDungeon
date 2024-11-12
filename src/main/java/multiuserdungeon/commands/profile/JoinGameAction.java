package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class JoinGameAction implements Action<Boolean> {
    
    private final User receiver;
    private final String filename;

    public JoinGameAction(User user, String filename) {
        this.receiver = user;
        this.filename = filename;
    }

    @Override
    public Boolean execute() {
        if (canExecute())
            return ((Profile) this.receiver).handleJoinGame(filename);

        return false;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof Profile)
            return true;

        return false;
    }
}
