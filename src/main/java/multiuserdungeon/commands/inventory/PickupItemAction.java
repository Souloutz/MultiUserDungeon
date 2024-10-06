package multiuserdungeon.commands.inventory;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class PickupItemAction implements Action<List<InventoryElement>> {

	private final Game receiver;
	private final int index;

	public PickupItemAction(Game game, int index) {
		this.receiver = game;
		this.index = index;
	}

	@Override
	public List<InventoryElement> execute() {
		return this.receiver.handlePickupItem(this.index);
	}

}
