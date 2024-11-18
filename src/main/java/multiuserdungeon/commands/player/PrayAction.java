package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class PrayAction implements Action<Boolean> {
    
    private final Game receiver;

    public PrayAction(Game game) {
        this.receiver = game;
    }

    @Override
    public Boolean execute() {
	    if(!canExecute()) return false;
	    return this.receiver.handlePray();
    }

    @Override
    public boolean canExecute() {
        return this.receiver != null;
    }
}
