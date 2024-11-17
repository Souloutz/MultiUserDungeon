package multiuserdungeon.map.tiles;

import java.util.HashMap;
import java.util.Map;

import multiuserdungeon.inventory.elements.*;
import multiuserdungeon.inventory.*;

public class Player extends Character {

	private final Inventory inventory;
	private Weapon weapon;
	private Armor armor;
	private final Map<Buff, Integer> buffs;
	private int gold;

	public Player(String name, String description, Inventory inventory, Map<Buff, Integer> buffs) {
		super(name, description, 100, 10, 0);
		this.inventory = inventory;
		this.weapon = null;
		this.armor = null;
		this.buffs = buffs;
		this.gold = 0;
	}

	public Player(Player player) {
		super(player.getName(), player.getDescription(), player.getMaxHealth(), player.getAttack(), player.getDefense());
		this.inventory = new Inventory(player.getInventory());
		this.weapon = player.getWeapon();
		this.armor = player.getArmor();
		this.buffs = new HashMap<>(player.buffs);
		this.gold = player.gold;
		this.setHealth(player.getHealth());
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Player p) {
			return getName().equals(p.getName()) && getDescription().equals(p.getDescription()) &&
					getMaxHealth() == p.getMaxHealth() && getAttack() == p.getAttack() && getDefense() == p.getDefense() &&
					this.weapon == p.weapon && this.armor == p.armor && this.buffs.equals(p.buffs) && this.gold == p.gold;
		}
		return false;
	}

	@Override
	public int getMaxHealth() {
		int health = super.getMaxHealth();

		for(Buff buff : this.buffs.keySet()) {
			if(buff.getStat() == BuffStat.HEALTH) {
				health += buff.getStatAmount();
			}
		}

		return health;
	}

	@Override
	public int getAttack() {
		int attack = super.getAttack();

		for(Buff buff : this.buffs.keySet()) {
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

		for(Buff buff : this.buffs.keySet()) {
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

	public Weapon getWeapon() {
		return this.weapon;
	}

	public void equipWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Armor getArmor() {
		return this.armor;
	}

	public void equipArmor(Armor armor) {
		this.armor = armor;
	}

	public Map<Buff, Integer> getBuffs() {
		return this.buffs;
	}

	public void useBuff(Buff buff) {
		this.buffs.put(buff, 0);
	}

	public void depleteBuffs() {
		this.buffs.replaceAll((buff, turnCounter) -> turnCounter + 1);
		this.buffs.entrySet().removeIf(entry -> entry.getValue() == 10);
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

	public int getGold() {
		return gold;
	}

	public void loseGold(int spent) {
		this.gold -= spent;
	}
	public void gainGold(int gain) {
		this.gold += gain;
	}

	@Override
	public char getASCII() {
		return 'P';
	}

	@Override
	public String toString() {
		return super.toString() + " (" + this.buffs.size() + " active buffs)";
	}

}
