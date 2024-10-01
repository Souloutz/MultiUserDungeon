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
		addBag(new Bag("Bag 0","Starter bag", 0, 6));
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

	public void addBag(Bag bag) {
		//TODO:add condition if inventory has 6 bags
		bags.add(bag);
	}

	public void removeBag(int bagPos) {


	}

	public void swapBag(int sourceBagPos, int destBagPos) {

	}

	public InventoryElement getItem(int bagPos, int itemPos) {
		return bags.get(itemPos).getItem(itemPos);
	}

	public Bag getBag(int bagPos) {
		return bags.get(bagPos);
	}

	public List<Bag> bags() {
		return bags;
	}

	/*
		displays bags' names and used/available space
		e.g)
		Bag 1: 2/5
		Bag 2: 0/8
		*/
	public String listBags(){
		String bagsString = "";
		for(Bag bag : bags){
			bagsString += bag.getName() + ": " + getOccupancy() + "/" + getCapacity() + "\n"; 
		}
		return bagsString;
	}

	@Override
	public String toString() {
		return name + "\n" + description + "Bags:\n" + listBags();
	}

}
