package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;

public class Weapon implements InventoryElement {

	private String name;
    private String description;
    private int goldValue;
    private int occupancy = 1;
    private int attack;

	public Weapon(String name, String description, int goldValue, int attack) {
		this.name = name;
        this.description = description;
        this.goldValue = goldValue;
        this.attack = attack;
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

	public int getAttack() {
		return attack;
	}

	@Override
	public String toString() {
		return name + "\n" + description + "\nGold Value: " + goldValue + "\nAttack Points: +" + attack;
	}

}
