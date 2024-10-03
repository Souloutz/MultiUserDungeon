package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class ViewItemAction implements Action<String> {
    
    Game receiver;
    int bagPos;
    int itemPos;

    public ViewItemAction(Game game, int bagPos, int itemPos) {
        receiver = game;
        this.bagPos = bagPos;
        this.itemPos = itemPos;
    }

    @Override
    public String execute() {
        return receiver.handleViewItem(bagPos, itemPos);
    }
}
