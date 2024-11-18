package multiuserdungeon.commands.player;

import java.util.List;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;
import multiuserdungeon.inventory.InventoryElement;

public class OpenChestAction implements Action<List<InventoryElement>> {

	private final Game receiver;

	public OpenChestAction(Game game) {
		this.receiver = game;
	}

	@Override
	public List<InventoryElement> execute() {
		if (!canExecute()) return null;
		return this.receiver.handleOpenChest();
	}

	@Override
	public boolean canExecute() {
		return this.receiver != null;
	}
}
