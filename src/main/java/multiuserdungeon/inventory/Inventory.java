package multiuserdungeon.inventory;

import multiuserdungeon.inventory.elements.Bag;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements InventoryElement {

	private final String name;
    private final String description;
	private final List<Bag> bags;

	public Inventory(String name, String description) {
		this.name = name;
		this.description = description;
		this.bags = new ArrayList<>();
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
		for(Bag bag : bags) {
			totalGV += bag.getGoldValue();
		}
		return totalGV;
	}

	@Override
	public int getOccupancy() {
		int totalOccupancy = 0;
		for(Bag bag : bags) {
			totalOccupancy += bag.getOccupancy();
		}
		return totalOccupancy;
	}

	public int getCapacity() {
		int totalCapacity = 0;
		for(Bag bag : bags) {
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
		return bags.get(bagPos).getItem(itemPos);
	}

	/**
	 * adds item into the next available bag
	 * @param item
	 * @return true if item is added, false if inventory completely full
	 */
	public boolean addItem(InventoryElement item){
		for(Bag bag : bags) {
			if(bag.getOccupancy()<bag.getCapacity()) {
				bag.addItem(item);
				return true;
			}
		}
		return false; 
	}

	public boolean bagExists(int bagPos){
		return (bagPos < bags.size() && bagPos >= 0);
	}

	public void addBag(Bag bag) {
		if(bags.size() >= 6) {
			if(addItem(bag)) {
				return; //returns if empty bag has been stored away
			}
			return; //returns if both inventory and all bags are full;
		}
		bags.add(bag);
		bag.setIsEquipped(true);
	}

	/**
	 * swaps an equipped bag in the inventory with a larger empty bag that's stored away
	 * @param sourceBagPos
	 * @param destBagPos
	 * @param destItemPos
	 */
	public void swapBag(int sourceBagPos, int destBagPos, int destItemPos) {
		if(bagExists(sourceBagPos) && bagExists(destBagPos) && getBag(destBagPos).itemExists(destItemPos)) {

			Bag sourceBag = getBag(sourceBagPos);
			Bag destBag = (Bag) getItem(destBagPos, destItemPos);
			if(destBag.getCapacity() < sourceBag.getCapacity()) {
				return; //return if new bag is smaller than source bag
			} else {
				for(InventoryElement item : sourceBag.items()) {
					destBag.addItem(item);
				}
				bags.remove(sourceBagPos);
				bags.add(sourceBagPos,destBag);
				getBag(destBagPos).removeItem(destItemPos);
				sourceBag.items().clear();
				addItem(sourceBag); 
				sourceBag.setIsEquipped(false);
				destBag.setIsEquipped(true);
			}
		} else {
			return; //return if either bags or item don't exist
		}
	}

	/*
		displays bag number and its items
		e.g)
		Bag 0: Apple, Sword, Spear
		Bag 1:
		*/
	public String listBags() {
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
		return name + "\n" + description + "\n" + listBags() + "Gold Value: " + getGoldValue() + "\nOccupancy: " + getOccupancy() + "/" + getCapacity();
	}

}
