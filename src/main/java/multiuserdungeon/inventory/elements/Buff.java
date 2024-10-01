package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.BuffStat;
import multiuserdungeon.inventory.InventoryElement;

public class Buff implements InventoryElement {

	public Buff(String name, String description, int goldValue, BuffStat stat, int statAmount) {

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

	public BuffStat getStat() {
		return null;
	}

	public int getStatAmount() {
		return 0;
	}

	@Override
	public String toString() {
		return "";
	}

}
