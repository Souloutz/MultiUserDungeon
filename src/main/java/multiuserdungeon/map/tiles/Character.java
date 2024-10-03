package multiuserdungeon.map.tiles;

import multiuserdungeon.map.Compass;
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

	}

	public void attacked(Compass direction, int attack) {

	}

	public String getDescription() {
		return null;
	}

	public int getBaseHealth() {
		return 0;
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
