package multiuserdungeon.commands;

import multiuserdungeon.Game;

public class QuitGameAction implements Action {

	private Game receiver;

	public QuitGameAction(Game game) {
		receiver = game;
	}

	@Override
	public void execute() {
		receiver.handleQuitGame();
	}
}
