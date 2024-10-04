package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class CloseChestAction implements Action<Void> {
    
    Game receiver;

    public CloseChestAction(Game game) {
        receiver = game;
    }

    @Override
    public Void execute() {
        receiver.handleCloseChest();
        return null;
    }
}
