package multiuserdungeon.commands.map;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.commands.responses.OpenChestResponse;

public class OpenChestAction implements Action<OpenChestResponse> {

	private Game receiver;

	public OpenChestAction(Game game) {
		receiver = game;
	}

	@Override
	public OpenChestResponse execute() {
		receiver.handleOpenChest();

		return new OpenChestResponse(null); // TODO !!!?!??
	}
}
