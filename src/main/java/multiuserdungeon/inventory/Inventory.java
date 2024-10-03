package multiuserdungeon.inventory;

import multiuserdungeon.inventory.elements.Bag;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements InventoryElement {
	private String name;
    private String description;
    private int goldValue = 0;
    private int occupancy = 0;
    private int capacity = 0;

	private final List<Bag> bags = new ArrayList<>();

	public Inventory(String name, String description) {
		this.name = name;
		this.description = description;
		addBag(new Bag("Starter bag","Default bag you start with", 0, 6));
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
		int totalGV = 0;
		for(Bag bag : bags){
			totalGV += bag.getGoldValue();
		}
		return totalGV;
	}

	@Override
	public int getOccupancy() {
		int totalOccupancy = 0;
		for(Bag bag : bags){
			totalOccupancy += bag.getOccupancy();
		}
		return totalOccupancy;
	}

	public int getCapacity() {
		int totalCapacity = 0;
		for(Bag bag : bags){
			totalCapacity += bag.getCapacity();
		}
		return totalCapacity;
	}

	public Bag getBag(int bagPos) {
		return bags.get(bagPos);
	}

	public List<Bag> bags() {
		return bags;
	}

	public InventoryElement getItem(int bagPos, int itemPos) {
		return bags.get(itemPos).getItem(itemPos);
	}

	/**
	 * adds item into the next available bag
	 * @param item
	 * @return true if item is added, false if inventory completely full
	 */
	public boolean additem(InventoryElement item){
		for(Bag bag : bags){
			if(bag.getOccupancy()<bag.getCapacity()){ 
				bag.addItem(item);
				return true;
			}
		}
		return false; 
	}

	public boolean bagExists(int bagPos){
		return (bagPos < bags.size() && bagPos > 0);
	}

	public void addBag(Bag bag) {
		//TODO:return response
		if(bags.size() >= 6){
			if(additem(bag)){
				return; //returns if empty bag has been stored away
			}
			return; //returns if both inventory and all bags are full;
		}
		bags.add(bag);
	}


	public void removeBag(int bagPos) {
		//TODO:return response
		if(bagExists(bagPos)){ 
			bags.remove(bagPos);
		}
		else{
			return; //return when bagPos invalid
		}

	}

	/**
	 * swaps an equipped bag in the inventory with a larger empty bag that's stored away
	 * @param sourceBagPos
	 * @param destBagPos
	 * @param destItemPos
	 */
	public void swapBag(int sourceBagPos, int destBagPos, int destItemPos) {
		//TODO:return response
		Bag sourceBag = getBag(sourceBagPos);
		Bag destBag = (Bag) getItem(destBagPos, destItemPos);
		if( destBag.getCapacity() < sourceBag.getCapacity()){ 
			return; //return if new bag is smaller than source bag
		}
		else{
			for(InventoryElement item : sourceBag.items()){
				destBag.addItem(item);
			}
			bags.remove(sourceBagPos);
			bags.add(sourceBagPos,destBag);
			getBag(destBagPos).removeItem(destItemPos);
			sourceBag.items().clear();
			additem(sourceBag); 
		}
	}

	/*
		displays bag number and its items
		e.g)
		Bag 0: Apple, Sword, Spear
		Bag 1:
		*/
	public String listBags(){
		String bagsString = "";
		int bagNum = 0;
		for(Bag bag : bags){
			bagsString += "Bag " + bagNum + ": " + bag.listItems() + "\n";
			bagNum++;
		}
		return bagsString;
	}

	@Override
	public String toString() {
		return name + "\n" + description + "\n" + listBags() + "Gold Value: " + goldValue + "\nOccupancy: " + getOccupancy() + "/" + getCapacity();
	}

}
