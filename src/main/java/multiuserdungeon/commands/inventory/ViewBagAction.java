package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class ViewBagAction implements Action<String> {
    
    Game receiver;
    int bagPos;

    public ViewBagAction(Game game, int bagPos) {
        receiver = game;
        this.bagPos = bagPos;
    }

    @Override
    public String execute() {
        return receiver.handleViewBag(bagPos);
    }
}
