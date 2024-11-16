package multiuserdungeon.commands.player;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.Compass;

public class TalkToMerchantAction implements Action<List<InventoryElement>> {
    
    private final Game receiver;
    private final User user;
    private final Compass direction;

    public TalkToMerchantAction(Game game, User user, Compass direction) {
        this.receiver = game;
        this.user = user;
        this.direction = direction;
    }

    @Override
    public List<InventoryElement> execute() {
        if (canExecute())
            return this.receiver.handleTalkToMerchant(direction);

        return null;
    }

    @Override
    public boolean canExecute() {
        //TODO{Make very specific to determine if this action should be an option}
        if (user instanceof Profile)
			return true;

		return false;
    }
}
