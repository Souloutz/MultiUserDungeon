package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.Profile;
import multiuserdungeon.commands.Action;

public class QuitGameAction implements Action<String> {

	private final Game receiver;
	private final Profile profile;

	public QuitGameAction(Game game, Profile profile) {
		this.receiver = game;
		this.profile = profile;
	}

	@Override
	public String execute() {
		if (canExecute())
			return this.receiver.handleQuitGame();

		return null;
	}	

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'canExecute'");
	}
}
