package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class ViewBagAction implements Action<String> {

    private final Game receiver;
    private final int bagPos;

    public ViewBagAction(Game game, int bagPos) {
        this.receiver = game;
        this.bagPos = bagPos;
    }

    @Override
    public String execute() {
	    if(!canExecute()) return null;
	    return this.receiver.handleViewBag(bagPos);
    }

    @Override
    public boolean canExecute() {
        return this.receiver != null;
    }

}
