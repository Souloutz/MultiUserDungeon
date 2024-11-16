package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.tiles.Player;

import java.util.ArrayList;
import java.util.List;

public class Bag implements InventoryElement {

	private final String name;
    private final String description;
    private final int goldValue;
    private final int capacity;
	private boolean isEquipped;
	private List<InventoryElement> items;

	public Bag(String name, String description, int goldValue, int capacity) {
		this.name = name;
		this.description = description;
		this.goldValue = goldValue;
		this.capacity = capacity;
		this.isEquipped = false;
		this.items = new ArrayList<>();
	}

	public Bag(Bag bag) {
		this.name = bag.name;
		this.description = bag.description;
		this.goldValue = bag.goldValue;
		this.capacity = bag.capacity;
		this.isEquipped = bag.isEquipped;
		this.items = new ArrayList<>(bag.items);
	}

	@Override
	public boolean equals (Object o) {
		if (o instanceof Bag b) {
			if (b.name.equals(this.name) &&
			b.description.equals(this.description) &&
			b.goldValue == this.goldValue &&
			b.capacity == this.capacity) {
				for (int i = 0; i < this.items.size(); i++) {
					try {
						if (!b.items.get(i).equals(this.items.get(i))) {
							break;
						}
					} catch (IndexOutOfBoundsException e) {
						return false;
					}
					if (i++ == this.items.size()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public int getGoldValue() {
		int totalGV = this.goldValue;
		for(InventoryElement item : this.items) {
			totalGV += item.getGoldValue();
		}
		return totalGV;
	}

	@Override
	public int getOccupancy() {
		if(this.isEquipped) {
			return this.items.size();
		}
		return 1;
	}

	@Override
	public boolean handleEquip(Player player) {
		return false;
	}

	@Override
	public boolean handleUse(Player player) {
		return false;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setIsEquipped(boolean equipped){
		this.isEquipped = equipped;
	}

	public List<InventoryElement> items() {
		return this.items;
	}

	public InventoryElement getItem(int itemPos) {
		return this.items.get(itemPos);
	}

	public void addItem(InventoryElement item) {
		this.items.add(item);
	}

	public void removeItem(int itemPos) {
		this.items.remove(itemPos);
	}

	public void clearItems() {
		this.items = new ArrayList<>();
	}

	@Override
	public String toString() {
		return this.name + ", " + this.description + " (" + getGoldValue() + "g, " + getOccupancy() + "/" + this.capacity + ")";
	}
	
}
