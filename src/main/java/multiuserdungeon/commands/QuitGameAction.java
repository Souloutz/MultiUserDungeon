package multiuserdungeon.commands;

import multiuserdungeon.Game;

public class QuitGameAction implements Action<Void> {

	private final Game receiver;

	public QuitGameAction(Game game) {
		this.receiver = game;
	}

	@Override
	public Void execute() {
		this.receiver.handleQuitGame();
		return null;
	}

}
