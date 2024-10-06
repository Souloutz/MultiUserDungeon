package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.BuffStat;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.tiles.Player;

public class Buff implements InventoryElement {

	private final String name;
    private final String description;
    private final int goldValue;
    private final BuffStat stat;
    private final int statAmount;

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
		return 1;
	}

	@Override
	public boolean handleEquip(Player player) {
		return false;
	}

	@Override
	public boolean handleUse(Player player) {
		player.useBuff(this);
		return true;
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
