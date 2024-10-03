package multiuserdungeon.map.tiles;

import multiuserdungeon.inventory.Inventory;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Weapon;
import multiuserdungeon.map.Tile;

public class Player extends Character {

	public Player(String name, String description) {
		super(name, description, 0, 0, 0);
	}

	public Inventory getInventory() {
		return null;
	}

	public void pickupItem(InventoryElement element) {

	}


	public void destroyItem(int bagPos, int itemPos) {

	}

	public void useBuff(int bagPos, int itemPos) {

	}

	public void useFood(int bagPos, int itemPos) {

	}

	public void swapBag(int sourceBagPos, int destBagPos) {

	}

	public Weapon getEquippedWeapon() {
		return null;
	}

	public Armor getEquippedArmor() {
		return null;
	}

	public void equipWeapon(InventoryElement item) {

	}

	public void equipArmor(InventoryElement item) {

	}

	public void unequipWeapon() {

	}

	public void unequipArmor() {

	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	public int getDefense() {
		return 0;
	}

	@Override
	public int getAttack() {
		return 0;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Tile getTile() {
		return null;
	}

	@Override
	public void setTile(Tile tile) {

	}

	@Override
	public boolean passable() {
		return false;
	}

	@Override
	public boolean isTrap() {
		return false;
	}

}
