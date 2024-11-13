package multiuserdungeon.commands.profile;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class JoinGameAction implements Action<Boolean> {
    
    private final User receiver;
    private final String filePath;

    public JoinGameAction(User user, String filePath) {
        this.receiver = user;
        this.filePath = filePath;
    }

    @Override
    public Boolean execute() {
        if (canExecute())
            return ((Profile) this.receiver).handleJoinGame(filePath);

        return false;
    }

    @Override
    public boolean canExecute() {
        if (receiver instanceof Profile)
            return true;

        return false;
    }
}
