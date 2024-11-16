package multiuserdungeon.commands.authentication;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;
import multiuserdungeon.persistence.PersistenceManager;

public class QuitAction implements Action<Void> {

	private final Authenticator auth;
	private final Game game;

	public QuitAction(Authenticator auth, Game game) {
		this.auth = auth;
		this.game = game;
	}

	@Override
	public Void execute() {
		if(!canExecute()) return null;
		if(this.auth.loggedIn()) {
			PersistenceManager.getInstance().saveProfile((Profile) this.auth.getUser());
		}
		if(this.game != null) this.game.handleQuitGame();
		return null;
	}	

	@Override
	public boolean canExecute() {
		return true;
	}

}
