package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.*;

public class PrayAction implements Action<Boolean> {
    
    private final Game receiver;
    private final User user;
    private final EndlessMap map;

    public PrayAction(Game game, User user, EndlessMap map) {
        this.receiver = game;
        this.user = user;
        this.map = map;
    }

    @Override
    public Boolean execute() {
        // TODO check map
        if (canExecute())
            return this.receiver.handlePray();

        return false;
    }

    @Override
    public boolean canExecute() {
        //TODO{Make very specific to determine if this action should be an option}
        if (user instanceof Profile && map instanceof EndlessMap)
			return true;

		return false;
    }
}
