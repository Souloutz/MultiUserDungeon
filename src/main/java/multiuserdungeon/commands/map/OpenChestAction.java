package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class OpenChestAction implements Action {

	private Game receiver;

	public OpenChestAction(Game game) {
		receiver = game;
	}

	@Override
	public void execute() {
		receiver.handleOpenChest();
	}
}
