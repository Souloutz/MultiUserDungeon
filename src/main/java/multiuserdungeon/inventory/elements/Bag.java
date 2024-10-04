package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

import java.util.List;

public class Bag implements InventoryElement {
	private String name;
    private String description;
    private int goldValue;
    private int occupancy = 1;
    private final int capacity;
	private boolean isEquipped;
	private List<InventoryElement> items;


	public Bag(String name, String description, int goldValue, int capacity) {
		this.name = name;
		this.description = description;
		this.goldValue = goldValue;
		this.capacity = capacity;
		this.isEquipped = false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getGoldValue() {
		int totalGV = goldValue;
		for(InventoryElement item : items){
			totalGV += item.getGoldValue();
		}
		return totalGV;
	}

	@Override
	public int getOccupancy() {
		if(isEquipped){
			return items.size();
		}
		return occupancy;
	}

	public int getCapacity() {
		return capacity;
	}

	public InventoryElement getItem(int itemPos) {
		return items.get(itemPos);
	}

	public List<InventoryElement> items() {
		return items;
	}

	public void setIsEquipped(boolean equipped){
		isEquipped = equipped;
	}

	public void addItem(InventoryElement item) {
		if(items.size()>=capacity){ //checks if bag is full
			return;
		}
		items.add(item);
	}

	public void removeItem(int itemPos) {
		if(itemPos < items.size() && itemPos >= 0){ //checks if item index exists
			items.remove(itemPos);
		}
		return; //return if item position is invalid
	}

	/*
	 * displays all items in the bag's names seperated by commas
	 * e.g.)
	 * Apple, Sword, Spear
	 */
	public String listItems(){
		String itemsString = "";
		for (int i =0; i<items.size()-1;i++){
			itemsString=items.get(i).getName() + ", ";
		}
		itemsString+=items.get(items.size()-1).getName();
		return itemsString;
	}
	@Override
	public String toString() {
		return name + "\n" + description + "\nItems: " + listItems() + "\nGold Value: " + getGoldValue()+ "\nOccupancy: " + getOccupancy() + "/" + capacity;
	}

	
}
