package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.BuffStat;
import multiuserdungeon.inventory.InventoryElement;

public class Buff implements InventoryElement {
	private String name;
    private String description;
    private int goldValue;
    private int occupancy = 1;
    private BuffStat stat;
    private int statAmount;

	public Buff(String name, String description, int goldValue, BuffStat stat, int statAmount) {
		this.name = name;
        this.description = description;
        this.goldValue = goldValue;
        this.stat = stat;
        this.statAmount = statAmount;
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

	public BuffStat getStat() {
		return stat;
	}

	public int getStatAmount() {
		return statAmount;
	}

	@Override
	public String toString() {
		return name + "\n" + description + "\nGold Value: " + goldValue + "\n" + stat.toString() + " Points: +" + statAmount;
	}

}
