package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.tiles.Player;

public class Weapon implements InventoryElement {

	private final String name;
    private final String description;
    private final int goldValue;
    private final int attack;

	public Weapon(String name, String description, int goldValue, int attack) {
		this.name = name;
        this.description = description;
        this.goldValue = goldValue;
        this.attack = attack;
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
		player.equipWeapon(this);
		return true;
	}

	@Override
	public boolean handleUse(Player player) {
		return false;
	}

	public int getAttack() {
		return this.attack;
	}

	@Override
	public String toString() {
		return this.name + "\n" + this.description + "\nGold Value: " + this.goldValue + "\nAttack Points: +" + this.attack;
	}

}
