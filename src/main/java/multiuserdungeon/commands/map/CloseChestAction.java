package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class CloseChestAction implements Action<Void> {
    
    private final Game receiver;

    public CloseChestAction(Game game) {
        this.receiver = game;
    }

    @Override
    public Void execute() {
        this.receiver.handleCloseChest();
        return null;
    }

}
