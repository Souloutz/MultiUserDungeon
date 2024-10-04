package multiuserdungeon.commands.map;

import java.util.Collection;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.commands.responses.OpenChestResponse;
import multiuserdungeon.inventory.InventoryElement;

public class OpenChestAction implements Action<OpenChestResponse> {

	private Game receiver;

	public OpenChestAction(Game game) {
		receiver = game;
	}

	@Override
	public OpenChestResponse execute() {
		Collection<InventoryElement> chestContents = receiver.handleOpenChest();

		return new OpenChestResponse(); // TODO !!!?!??
	}
}
