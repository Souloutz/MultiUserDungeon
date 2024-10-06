package multiuserdungeon.map.tiles;

import java.util.ArrayList;

import multiuserdungeon.inventory.Inventory;
import multiuserdungeon.inventory.elements.Armor;
import multiuserdungeon.inventory.elements.Weapon;
import multiuserdungeon.map.Tile;
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

	public Inventory getInventory() {
		return inventory;
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
		this.gainHealth(food.getHealth());
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

}
