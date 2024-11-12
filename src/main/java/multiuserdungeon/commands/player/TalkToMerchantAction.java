package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.map.Compass;

public class TalkToMerchantAction implements Action<String> {
    
    private final Game receiver;
    private final User user;
    private final Compass direction;

    public TalkToMerchantAction(Game game, User user, Compass direction) {
        this.receiver = game;
        this.user = user;
        this.direction = direction;
    }

    @Override
    public String execute() {
        if (canExecute())
            return this.receiver.handleTalkToMerchant(direction);

        return null;
    }

    @Override
    public boolean canExecute() {
       if (user instanceof Profile)
			return true;

		return false;
    }
}
