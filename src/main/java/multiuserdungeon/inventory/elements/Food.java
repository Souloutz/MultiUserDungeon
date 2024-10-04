package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

public class Food implements InventoryElement {
	private String name;
    private String description;
    private int goldValue;
    private int occupancy = 1;
	private int health;


	public Food(String name, String description, int goldValue, int health) {
		this.name = name;
		this.description = description;
		this.goldValue = goldValue;
		this.health = health;
		
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

	public int getHealth() {
		return health;
	}

	@Override
	public String toString() {
		return name + "\n" + description + "\nGold Value: " + goldValue + "\nHealth Points: +" + health;
	}

}
