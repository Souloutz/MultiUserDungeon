package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.tiles.Player;

public class Armor implements InventoryElement {

	private final String name;
    private final String description;
    private final int goldValue;
    private final int defense;

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
		return 1;
	}

	@Override
	public boolean handleEquip(Player player) {
		player.equipArmor(this);
		return true;
	}

	@Override
	public boolean handleUse(Player player) {
		return false;
	}

	public int getDefense() {
		return defense;
	}

	@Override
	public String toString() {
		return name + "\n" + description + "\nGold Value: " + goldValue + "\nDefense Points: +" + defense;
	}

}
