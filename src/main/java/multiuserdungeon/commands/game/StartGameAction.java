package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class StartGameAction implements Action<Game> {
    
    private final Authenticator auth;
    private final String filePath;

    public StartGameAction(Authenticator auth, String filePath) {
        this.auth = auth;
        this.filePath = filePath;
    }

    @Override
    public Game execute() {
	    if(!canExecute()) return null;
	    return ((Profile) this.auth.getUser()).handleStartGame(this.filePath);
    }

    @Override
    public boolean canExecute() {
        return this.auth.loggedIn();
    }

}
