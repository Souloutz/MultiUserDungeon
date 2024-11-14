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

	//copy constructor
	public Buff(Buff buff) {
		this.name = buff.name;
        this.description = buff.description;
        this.goldValue = buff.goldValue;
        this.stat = buff.stat;
        this.statAmount = buff.statAmount;
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
		return this.goldValue;
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
		return this.stat;
	}

	public int getStatAmount() {
		return this.statAmount;
	}

	@Override
	public String toString() {
		return this.name + ", " + this.description + " (" + this.goldValue + "g, +" + this.statAmount + " " + this.stat + ")";
	}

}
