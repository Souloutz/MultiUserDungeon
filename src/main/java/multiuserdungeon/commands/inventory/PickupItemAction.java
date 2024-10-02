package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class PickupItemAction implements Action {

	private Game receiver;
	private InventoryElement item;

	public PickupItemAction(Game game, InventoryElement item) {
		receiver = game;
		this.item = item;
	}

	@Override
	public void execute() {
		receiver.handlePickupItem(item);
	}
}
