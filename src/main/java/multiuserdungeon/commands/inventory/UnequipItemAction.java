package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class UnequipItemAction implements Action {

	Game receiver;
	boolean isWeapon; // Can only equip weapons or armor

	public UnequipItemAction(Game game, boolean isWeapon) {
		receiver = game;
		this.isWeapon = isWeapon;
	}

	@Override
	public void execute() {
		receiver.handleUnequipItem(isWeapon);
	}
}
