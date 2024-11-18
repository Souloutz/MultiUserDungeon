package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class CloseAction implements Action<Void> {
    
    private final Game receiver;

    public CloseAction(Game game) {
        this.receiver = game;
    }

    @Override
    public Void execute() {
	    if(!canExecute()) return null;
	    this.receiver.handleClose();
	    return null;
    }

    @Override
    public boolean canExecute() {
        return this.receiver != null;
    }
}
