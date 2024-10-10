package multiuserdungeon.inventory.elements;

import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.map.tiles.Player;

public class Food implements InventoryElement {

	private final String name;
	private final String description;
	private final int goldValue;
	private final int health;

	public Food(String name, String description, int goldValue, int health) {
		this.name = name;
		this.description = description;
		this.goldValue = goldValue;
		this.health = health;
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
		player.useFood(this);
		return true;
	}

	public int getHealth() {
		return this.health;
	}

	@Override
	public String toString() {
		return this.name + ", " + this.description + " (" + this.goldValue + "g, +" + this.health + " health)";
	}

}
