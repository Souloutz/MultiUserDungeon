package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class EquipItemAction implements Action<Boolean> {

	private final Game receiver;
	private final int bagPos;
	private final int itemPos;

	public EquipItemAction(Game game, int bagPos, int itemPos) {
		this.receiver = game;
		this.bagPos = bagPos;
		this.itemPos = itemPos;
	}

	@Override
	public Boolean execute() {
		return this.receiver.handleEquipItem(this.bagPos, this.itemPos);
	}
}
