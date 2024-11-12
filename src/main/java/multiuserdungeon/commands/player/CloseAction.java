package multiuserdungeon.commands.player;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class CloseAction implements Action<Void> {
    
    private final Game receiver;
    private final Profile profile;

    public CloseAction(Game game, Profile profile) {
        this.receiver = game;
        this.profile = profile;
    }

    @Override
    public Void execute() {
        if (canExecute()) {
            this.receiver.handleCloseChest();
        }

        return null;
    }

    @Override
    public boolean canExecute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
    }
}
