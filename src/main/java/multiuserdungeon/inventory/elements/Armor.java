package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

public class Armor implements InventoryElement {

	public Armor(String name, String description, int goldValue, int defense) {

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

	public int getDefense() {
		return 0;
	}

}
