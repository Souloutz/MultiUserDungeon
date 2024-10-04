package multiuserdungeon.commands.map;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class OpenChestAction implements Action<String> {

	private Game receiver;

	public OpenChestAction(Game game) {
		receiver = game;
	}

	@Override
	public String execute() {
		List<InventoryElement> chestContents = receiver.handleOpenChest();

		int index = 0;
		String chestString = "";
		for (InventoryElement content : chestContents)
			chestString += index++ + ". " + content.getName() + "\n";

		return chestString;
	}
}
