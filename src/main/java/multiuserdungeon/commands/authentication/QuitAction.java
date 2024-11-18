package multiuserdungeon.commands.authentication;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Authenticator;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;
import multiuserdungeon.persistence.PersistenceManager;

public class QuitAction implements Action<Void> {

	private final Authenticator auth;
	private final Game receiver;

	public QuitAction(Authenticator auth, Game game) {
		this.auth = auth;
		this.receiver = game;
	}

	@Override
	public Void execute() {
		if(!canExecute()) return null;
		if(this.receiver != null) this.receiver.handleQuitGame();
		if(this.auth.loggedIn()) {
			PersistenceManager.getInstance().saveProfile((Profile) this.auth.getUser());
		}
		return null;
	}	

	@Override
	public boolean canExecute() {
		return true;
	}

}
