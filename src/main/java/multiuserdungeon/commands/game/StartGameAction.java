package multiuserdungeon.commands.game;

import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class StartGameAction implements Action<Boolean> {
    
    private final Profile receiver;

    public StartGameAction(Profile profile) {
        this.receiver = profile;
    }

    @Override
    public Boolean execute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public boolean canExecute() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
    }
}
