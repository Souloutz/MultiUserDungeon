package multiuserdungeon.map.tiles;

import java.util.List;

import multiuserdungeon.map.*;

public abstract class Character implements TileObject {

	Tile tile;

	String name;
	String description;

	int maxHealth;
	int health;
	int attack;
	int defense;

	public Character(String name, String description, int baseHealth, int baseAttack, int baseDefense) {
		this.name = name;
		this.description = description;
		this.maxHealth = baseHealth;
		this.health = baseHealth;
		this.attack = baseAttack;
		this.defense = baseDefense;
	}

	public void attack(Compass compass) {
		List<TileObject> objects = getTile().getTile(compass).getObjects();
		for (TileObject object : objects) {
			if (object instanceof Character character) {
				character.attacked(getAttack());
			}
		}
	}

	public void attacked(int attack) {
		this.health -= Math.max(1, attack - getDefense());
		this.health = Math.max(0, this.health);
	}

	public String getDescription() {
		return description;
	}

	public int getBaseHealth() {
		return maxHealth;
	}

	public int getBaseAttack() {
		return attack;
	}

	public int getBaseDefense() {
		return defense;
	}

	public void gainHealth(int health) {
		this.health += health;
		if (this.health > maxHealth){
			this.health = maxHealth;
		}
	}

	abstract int getHealth();

	abstract int getDefense();

	abstract int getAttack();

}
