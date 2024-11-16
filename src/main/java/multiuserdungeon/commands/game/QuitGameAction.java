package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;
import multiuserdungeon.persistence.PersistenceManager;

public class QuitGameAction implements Action<Void> {

	private final Authenticator auth;
	private final Game game;

	public QuitGameAction(Authenticator auth, Game game) {
		this.auth = auth;
		this.game = game;
	}

	@Override
	public Void execute() {
		if(!canExecute()) return null;
		if(this.auth.loggedIn()) {
			PersistenceManager.getInstance().saveProfile((Profile) this.auth.getUser());
		}
		if(this.game != null) {
			PersistenceManager.getInstance().saveGame(this.game);
		}
		return null;
	}	

	@Override
	public boolean canExecute() {
		return true;
	}

}
