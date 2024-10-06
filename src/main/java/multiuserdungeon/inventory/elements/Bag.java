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

	/*
	 * displays all items in the bag's names seperated by commas
	 * e.g.)
	 * Apple, Sword, Spear
	 */
	public String listItems(){
		String itemsString = "";
		for(int i = 0; i < this.items.size() - 1;i++) {
			itemsString = this.items.get(i).getName() + ", ";
		}
		itemsString += this.items.get(this.items.size() - 1).getName();
		return itemsString;
	}

	@Override
	public String toString() {
		return this.name + "\n" + this.description + "\nItems: " + listItems() + "\nGold Value: " + getGoldValue()+ "\nOccupancy: " + getOccupancy() + "/" + this.capacity;
	}
	
}
