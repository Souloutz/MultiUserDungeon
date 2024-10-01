package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

import java.util.List;

public class Bag implements InventoryElement {
	private String name;
    private String description;
    private int goldValue;
    private int occupancy = 0;
    private int capacity;
	private List<InventoryElement> items;

	public Bag(String name, String description, int goldValue, int capacity) {
		this.name = name;
		this.description = description;
		this.goldValue = goldValue;
		this.capacity = capacity;
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
		return items.size();
	}

	public int getCapacity() {
		return capacity;
	}

	public void addItem(InventoryElement item) {
		occupancy++;
	}

	public void removeItem(int itemPos) {

	}

	public InventoryElement getItem(int itemPos) {
		return items.get(itemPos);
	}

	public List<InventoryElement> items() {
		return items;
	}

	/*
	 * displays all items in the bag's names seperated by commas
	 * e.g.)
	 * apple, sword, spear
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
