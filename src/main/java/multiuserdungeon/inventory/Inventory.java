package multiuserdungeon.inventory;

import multiuserdungeon.inventory.elements.Bag;

import java.util.List;

public class Inventory implements InventoryElement {

	private final List<Bag> bags;

	public Inventory(String name, String description) {
		this.bags = null; // TODO: change this
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

	public int getCapacity() {
		return 0;
	}

	public void addBag(Bag bag) {

	}

	public void removeBag(int bagPos) {

	}

	public void swapBag(int sourceBagPos, int destBagPos) {

	}

	public InventoryElement getItem(int bagPos, int itemPos) {
		return null;
	}

	public Bag getBag(int bagPos) {
		return null;
	}

	public List<Bag> bags() {
		return null;
	}

	@Override
	public String toString() {
		return "";
	}

}
