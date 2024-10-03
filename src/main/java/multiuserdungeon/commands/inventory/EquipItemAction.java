package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class EquipItemAction implements Action<Void> {

	private Game receiver;
	private int bagPos;
	private int itemPos;
	private boolean isWeapon; // Can only equip weapons or armor

	public EquipItemAction(Game game, int bagPos, int itemPos, boolean isWeapon) {
		receiver = game;
		this.bagPos = bagPos;
		this.itemPos = itemPos;
	}

	@Override
	public Void execute() {
		receiver.handleEquipItem(bagPos, itemPos, isWeapon);
		return null;
	}
}
