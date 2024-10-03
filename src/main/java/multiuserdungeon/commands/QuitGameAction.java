package multiuserdungeon.commands;

import multiuserdungeon.Game;

public class QuitGameAction implements Action<Void> {

	private Game receiver;

	public QuitGameAction(Game game) {
		receiver = game;
	}

	@Override
	public Void execute() {
		receiver.handleQuitGame();
		return null;
	}
}
