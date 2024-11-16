package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class SaveGameAction implements Action<String> {
    
    private final Game receiver;
    private final User user;

    public SaveGameAction(Game game, User user) {
        this.receiver = game;
        this.user = user;
    }

    @Override
    public String execute() {
        if (canExecute())
            return this.receiver.handleSaveGame();
        
        return null;
    }

    @Override
    public boolean canExecute() {
        if (user instanceof Profile)
			return true;

		return false;
    }
}
