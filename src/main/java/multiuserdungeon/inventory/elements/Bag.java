package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

import java.util.List;

public class Bag implements InventoryElement {

	private final List<InventoryElement> items;

	public Bag(String name, String description, int goldValue, int capacity) {
		this.items = null; // TODO: change this
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

	public void addItem(InventoryElement item) {

	}

	public void removeItem(int itemPos) {

	}

	public InventoryElement getItem(int itemPos) {
		return null;
	}

	public List<InventoryElement> items() {
		return null;
	}

}
