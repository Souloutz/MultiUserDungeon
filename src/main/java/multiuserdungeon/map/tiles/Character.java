package multiuserdungeon.map.tiles;

import multiuserdungeon.map.*;

public abstract class Character implements TileObject {

	private final String name;
	private final String description;
	private int health;
	private final int baseMaxHealth;
	private final int baseAttack;
	private final int baseDefense;
	private Tile tile;

	public Character(String name, String description, int baseMaxHealth, int baseAttack, int baseDefense) {
		this.name = name;
		this.description = description;
		this.health = baseMaxHealth;
		this.baseMaxHealth = baseMaxHealth;
		this.baseAttack = baseAttack;
		this.baseDefense = baseDefense;
		this.tile = null;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Tile getTile() {
		return this.tile;
	}

	@Override
	public void setTile(Tile tile) {
		this.tile = tile;
	}

	@Override
	public boolean passable() {
		return false;
	}

	public String getDescription() {
		return this.description;
	}

	public int attack(Compass compass) {
		Tile tile = getTile().getTile(compass);
		if(tile == null) return -1;

		for(TileObject object : tile.getObjects()) {
			if(object instanceof Character character) {
				return character.attacked(getAttack());
			}
		}

		return -1;
	}

	public int getHealth() {
		return this.health;
	}

	public void replenishHealth(int amount) {
		this.health += Math.min(amount, getMaxHealth() - this.health);
	}

	public int attacked(int attack) {
		int damage = Math.max(1, attack - getDefense());
		this.health -= damage;
		this.health = Math.max(0, this.health);
		return damage;
	}

	public int getMaxHealth() {
		return this.baseMaxHealth;
	}

	public int getAttack() {
		return this.baseAttack;
	}

	public int getDefense() {
		return this.baseDefense;
	}

	@Override
	public String toString() {
		return this.name + ", " + this.description + " (" + this.health + "/" + getMaxHealth() + " health, " + getAttack() + " attack, " + getDefense() + " defense)";
	}

}
