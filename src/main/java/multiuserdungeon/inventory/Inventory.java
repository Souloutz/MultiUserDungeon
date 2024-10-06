package multiuserdungeon.inventory;

import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.map.tiles.Player;

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

	@Override
	public boolean handleEquip(Player player) {
		return false;
	}

	@Override
	public boolean handleUse(Player player) {
		return false;
	}

	public int getCapacity() {
		int totalCapacity = 0;
		for(Bag bag : this.bags) {
			totalCapacity += bag.getCapacity();
		}
		return totalCapacity;
	}

	public boolean addItem(InventoryElement item) {
		for(Bag bag : this.bags) {
			if(bag.getOccupancy() < bag.getCapacity()) {
				bag.addItem(item);
				return true;
			}
		}
		return false;
	}

	public boolean removeItem(int bagPos, int itemPos) {
		if(!itemExists(bagPos, itemPos)) return false;
		this.bags.get(bagPos).removeItem(itemPos);
		return true;
	}

	public InventoryElement getItem(int bagPos, int itemPos) {
		if(!itemExists(bagPos, itemPos)) return null;
		return this.bags.get(bagPos).getItem(itemPos);
	}

	private boolean itemExists(int bagPos, int itemPos) {
		if(bagPos >= this.bags.size()) return false;
		return itemPos < this.bags.get(itemPos).getOccupancy();
	}

	public boolean addBag(Bag bag) {
		if(this.bags.size() >= 6) {
			return addItem(bag);
		}

		this.bags.add(bag);
		bag.setIsEquipped(true);
		return true;
	}

	public boolean swapBag(int sourceBagPos, int destBagPos, int destItemPos) {
		if(sourceBagPos >= this.bags.size() || !itemExists(destBagPos, destItemPos)) return false;

		Bag sourceBag = this.bags.get(sourceBagPos);
		Bag destBag = (Bag) getItem(destBagPos, destItemPos);

		if(destBag.getCapacity() < sourceBag.getCapacity()) return false;

		for(InventoryElement item : sourceBag.items()) {
			destBag.addItem(item);
		}
		sourceBag.clearItems();

		this.bags.add(sourceBagPos, destBag);
		this.bags.remove(sourceBagPos);

		addItem(sourceBag);
		removeItem(destBagPos, destItemPos);

		sourceBag.setIsEquipped(false);
		destBag.setIsEquipped(true);
		return true;
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
		for(Bag bag : this.bags){
			bagsString += "Bag " + bagNum + ": " + bag.listItems() + "\n";
			bagNum++;
		}
		return bagsString;
	}

	@Override
	public String toString() {
		return this.name + "\n" + this.description + "\n" + listBags() + "Gold Value: " + getGoldValue() + "\nOccupancy: " + getOccupancy() + "/" + getCapacity();
	}

}
