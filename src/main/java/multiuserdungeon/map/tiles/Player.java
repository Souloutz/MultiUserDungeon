package multiuserdungeon.map.tiles;

import java.util.ArrayList;

import multiuserdungeon.inventory.Inventory;
import multiuserdungeon.inventory.InventoryElement;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Weapon;
import multiuserdungeon.map.Tile;
import multiuserdungeon.inventory.elements.*;
import multiuserdungeon.inventory.*;

public class Player extends Character {

	private Inventory inventory;
	private ArrayList<Buff> buffs;
	private Weapon weapon;
	private Armor armor;

	public Player(String name, String description) {
		super(name, description, 100, 10, 0);
		this.weapon = null;
		this.armor = null;
		this.inventory = new Inventory("Player's Inventory","Filled with all of your wonderful items.");
		this.buffs = new ArrayList<Buff>();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void pickupItem(InventoryElement element) {
		for (int i = 0; i < 6;i++) {
			if (inventory.getBag(i).getOccupancy() >= 6) {
				continue;
			} else {
				inventory.getBag(i).addItem(element);
			}
		}
	}

	public void destroyItem(int bagPos, int itemPos) {
		inventory.getBag(bagPos).removeItem(itemPos);
	}

	public void useBuff(int bagPos, int itemPos) {
		InventoryElement mayBuff = inventory.getItem(bagPos,itemPos);
		Buff buff;
		if (mayBuff instanceof Buff) {
			buff = (Buff)mayBuff;
		} else {
			return;
		}
		destroyItem(bagPos,itemPos);
		buffs.add(buff);
	}

	public void useFood(int bagPos, int itemPos) {
		InventoryElement mayFood = inventory.getItem(bagPos,itemPos);
		Food food;
		if (mayFood instanceof Food) {
			food = (Food)mayFood;
		} else {
			return;
		}
		destroyItem(bagPos,itemPos);
		this.gainHealth(food.getHealth());
	}

	public void swapBag(int sourceBagPos, int destBagPos) {
		inventory.swapBag(sourceBagPos,destBagPos);
	}

	public Weapon getEquippedWeapon() {
		return weapon;
	}

	public Armor getEquippedArmor() {
		return armor;
	}

	public void equipWeapon(InventoryElement item) {
		// TODO
	}

	public void equipArmor(InventoryElement item) {
		// TODO
	}

	public void unequipWeapon() {
		pickupItem(weapon);
		weapon = null;
	}

	public void unequipArmor() {
		pickupItem(armor);
		armor = null;
	}

	@Override
	public int getHealth() {
		return 0;
	}

	@Override
	int getDefense() {
		int buffDefense = 0;
		for (Buff buff : buffs){
			if (buff.getStat() == BuffStat.DEFENSE) {
				buffDefense += buff.getStatAmount();
			}
		}
		return defense + buffDefense + armor.getDefense();
	}

	@Override
	int getAttack() {
		int buffAttack = 0;
		for (Buff buff : buffs){
			if (buff.getStat() == BuffStat.ATTACK) {
				buffAttack += buff.getStatAmount();
			}
		}
		return attack + buffAttack + weapon.getAttack();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Tile getTile() {
		return tile;
	}

	@Override
	public void setTile(Tile tile) {
		this.tile = tile;
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
