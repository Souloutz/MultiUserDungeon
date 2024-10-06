package multiuserdungeon.map.tiles;

import java.util.ArrayList;

import multiuserdungeon.inventory.Inventory;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Weapon;
import multiuserdungeon.inventory.elements.*;
import multiuserdungeon.inventory.*;

public class Player extends Character {

	private final Inventory inventory;
	private Weapon weapon;
	private Armor armor;
	private final ArrayList<Buff> buffs;

	public Player(String name, String description) {
		super(name, description, 100, 10, 0);
		this.inventory = new Inventory("Player's Inventory", "Filled with all of your wonderful items.");
		this.weapon = null;
		this.armor = null;
		this.buffs = new ArrayList<>();
	}

	@Override
	public int getMaxHealth() {
		int health = super.getMaxHealth();

		for(Buff buff : this.buffs) {
			if(buff.getStat() == BuffStat.HEALTH) {
				health += buff.getStatAmount();
			}
		}

		return health;
	}

	@Override
	public int getAttack() {
		int attack = super.getAttack();

		for(Buff buff : this.buffs) {
			if(buff.getStat() == BuffStat.ATTACK) {
				attack += buff.getStatAmount();
			}
		}

		int weaponBuff = this.weapon != null ? this.weapon.getAttack() : 0;
		return attack + weaponBuff;
	}

	@Override
	public int getDefense() {
		int defense = super.getDefense();

		for(Buff buff : this.buffs) {
			if(buff.getStat() == BuffStat.DEFENSE) {
				defense += buff.getStatAmount();
			}
		}

		int armorBuff = this.armor != null ? this.armor.getDefense() : 0;
		return defense + armorBuff;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public void equipWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void equipArmor(Armor armor) {
		this.armor = armor;
	}

	public void useBuff(Buff buff) {
		this.buffs.add(buff);
	}

	public void useFood(Food food) {
		this.replenishHealth(food.getHealth());
	}

	public boolean unequipWeapon() {
		if(this.weapon == null) return false;
		this.inventory.addItem(this.weapon);
		this.weapon = null;
		return true;
	}

	public boolean unequipArmor() {
		if(this.armor == null) return false;
		this.inventory.addItem(this.armor);
		this.armor = null;
		return true;
	}

}
