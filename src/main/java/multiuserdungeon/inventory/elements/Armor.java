package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

public class Armor implements InventoryElement {
	private String name;
    private String description;
    private int goldValue;
    private int occupancy = 1;
    private int defense;

	public Armor(String name, String description, int goldValue, int defense) {
		this.name = name;
        this.description = description;
        this.goldValue = goldValue;
        this.defense = defense;

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
		return goldValue;
	}

	@Override
	public int getOccupancy() {
		return occupancy;
	}

	public int getDefense() {
		return defense;
	}

	@Override
	public String toString() {
		return name + "\n" + description + "\nGold Value: " + goldValue + "\nDefense Points: +" + defense;
	}

}
