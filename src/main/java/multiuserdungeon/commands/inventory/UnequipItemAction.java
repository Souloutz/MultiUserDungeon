package multiuserdungeon.commands.inventory;

import multiuserdungeon.Game;
import multiuserdungeon.commands.Action;

public class UnequipItemAction implements Action<Boolean> {

	private final Game receiver;
	private final boolean isWeapon;

	public UnequipItemAction(Game game, boolean isWeapon) {
		this.receiver = game;
		this.isWeapon = isWeapon;
	}

	@Override
	public Boolean execute() {
		return this.receiver.handleUnequipItem(this.isWeapon);
	}

}
