package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class JoinGameAction implements Action<Boolean> {
    
    private final Profile receiver;
    private final Game game;

    public JoinGameAction(Profile profile, Game game) {
        this.receiver = profile;
        this.game = game;
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
