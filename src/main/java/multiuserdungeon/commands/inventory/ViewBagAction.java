package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class ViewBagAction implements Action<String> {

    private final Game receiver;
    private final User user;
    private final int bagPos;

    public ViewBagAction(Game game, User user, int bagPos) {
        this.receiver = game;
        this.user = user;
        this.bagPos = bagPos;
    }

    @Override
    public String execute() {
        if (canExecute())
            return this.receiver.handleViewBag(bagPos);

        return null;
    }

    @Override
    public boolean canExecute() {
        if (user instanceof Profile)
            return true;
        
        return false;
    }
}
