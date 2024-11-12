package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Map;

public class PrayAction implements Action<Boolean> {
    
    private final Game receiver;
    private final User user;
    private final Map map;

    public PrayAction(Game game, User user, Map map) {
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
        if (user instanceof Profile)
			return true;

		return false;
    }
}
