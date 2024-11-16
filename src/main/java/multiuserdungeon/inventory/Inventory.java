package multiuserdungeon.inventory;

import multiuserdungeon.inventory.elements.Bag;
import multiuserdungeon.map.tiles.Player;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements InventoryElement {

	private final String name;
    private final String description;
	private final List<Bag> bags;

	public Inventory(String name, String description, boolean starterBag) {
		this.name = name;
		this.description = description;
		this.bags = new ArrayList<>();
		if(starterBag) addBag(new Bag("Starter Bag", "Default bag you start with", 0, 6));
	}

	public Inventory(Inventory inventory) {
		this.name = inventory.getName();
		this.description = inventory.getDescription();
		this.bags = new ArrayList<>();
		for(Bag bag : inventory.getBags()){
			Bag newBag = new Bag(bag.getName(), bag.getDescription(), bag.getGoldValue(), bag.getCapacity());
			for(InventoryElement item : bag.items()){
				newBag.addItem(item);
			}
			this.addBag(newBag);
		}
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
		int totalGV = 0;
		for(Bag bag : this.bags) {
			totalGV += bag.getGoldValue();
		}
		return totalGV;
	}

	@Override
	public int getOccupancy() {
		int totalOccupancy = 0;
		for(Bag bag : this.bags) {
			totalOccupancy += bag.getOccupancy();
		}
		return totalOccupancy;
	}

	public List<Bag> getBags() {
		return bags;
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
		return itemPos < this.bags.get(bagPos).getOccupancy();
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

	public String viewBag(int bagPos) {
		if(bagPos >= this.bags.size()) return null;
		Bag bag = this.bags.get(bagPos);
		StringBuilder builder = new StringBuilder(bag.toString());
		for(int i = 0; i < bag.items().size(); i++) {
			builder.append("\n\t").append(i).append(": ").append(bag.items().get(i).toString());
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.name + ", " + this.description + " (" + getGoldValue() + "g, " + getOccupancy() + "/" + getCapacity() + ")");
		for(int i = 0; i < this.bags.size(); i++) {
			builder.append("\n\t").append(i).append(": ").append(this.bags.get(i));
		}
		return builder.toString();
	}

}
