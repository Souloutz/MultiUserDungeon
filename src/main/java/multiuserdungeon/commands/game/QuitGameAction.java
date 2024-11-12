package multiuserdungeon.commands.game;

import multiuserdungeon.Game;
import multiuserdungeon.authentication.User;
import multiuserdungeon.commands.Action;

public class QuitGameAction implements Action<Void> {

	private final Game receiver;
	private final User user;

	public QuitGameAction(Game game, User user) {
		this.receiver = game;
		this.user = user;
	}

	@Override
	public Void execute() {
		if (canExecute())
			this.receiver.handleQuitGame();

		return null;
	}	

	@Override
	public boolean canExecute() {
		if (user instanceof User)
			return true;

		return false;
	}
}
