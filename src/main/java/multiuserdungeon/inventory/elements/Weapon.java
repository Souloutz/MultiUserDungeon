package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

public class Weapon implements InventoryElement {

	public Weapon(String name, String description, int goldValue, int attack) {

	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public int getGoldValue() {
		return 0;
	}

	@Override
	public int getOccupancy() {
		return 0;
	}

	public int getAttack() {
		return 0;
	}

	@Override
	public String toString() {
		return "";
	}

}
