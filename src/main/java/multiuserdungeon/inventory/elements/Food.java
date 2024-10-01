package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

public class Food implements InventoryElement {

	public Food(String name, String description, int goldValue, int health) {

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

	public int getHealth() {
		return 0;
	}

	@Override
	public String toString() {
		return "";
	}

}
