package multiuserdungeon.commands.inventory;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class PickupItemAction implements Action<String> {

	private Game receiver;
	private int index;

	public PickupItemAction(Game game, int index) {
		receiver = game;
		this.index = index;
	}

	@Override
	public String execute() {
		List<InventoryElement> chestContents = receiver.handlePickupItem(index);

		String chestString = "";
		if (chestContents.size() == 0)
			chestString += "Chest is empty";
		else {
			int chestIndex = 0;
			for (InventoryElement content : chestContents)
				chestString += chestIndex++ + ". " + content.getName() + "\n";
		}

		return chestString;
	}
}
