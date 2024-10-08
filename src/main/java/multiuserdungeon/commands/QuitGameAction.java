package multiuserdungeon.commands;

import multiuserdungeon.Game;

public class QuitGameAction implements Action<String> {

	private final Game receiver;

	public QuitGameAction(Game game) {
		this.receiver = game;
	}

	@Override
	public String execute() {
		return this.receiver.handleQuitGame();
	}

}
