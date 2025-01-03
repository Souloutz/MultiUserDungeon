package multiuserdungeon.map.tiles;

import multiuserdungeon.inventory.Items;
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
	
	public void setHealth(int health) {
		this.health = health;
	}

	public void replenishHealth(int amount) {
		this.health += Math.min(amount, getMaxHealth() - this.health);
	}

	public int attacked(int attack) {
		int damage = Math.max(1, attack - getDefense());
		if(damage > this.health) damage = this.health;
		this.health -= damage;

		if(this.health == 0) {
			Corpse corpse = new Corpse(this.name + "'s Corpse", Items.getInstance().getRandomList(1));
			corpse.setTile(this.tile);
			this.tile.addObject(corpse);
			this.tile.removeObject(this);
			this.tile = null;
		}

		return damage;
	}

	public int getMaxHealth() {
		return this.baseMaxHealth;
	}

	public int getBaseAttack() {
		// This is used in persistence
		return this.baseAttack;
	}

	public int getAttack() {
		// This version is overridden by Player/NPC
		return this.baseAttack;
	}

	public int getBaseDefense() {
		// This is used in persistence
		return this.baseDefense;
	}

	public int getDefense() {
		// This version is overridden by Player/NPC
		return this.baseDefense;
	}

	@Override
	public String toString() {
		return this.name + ", " + this.description + " (" + this.health + "/" + getMaxHealth() + " health, " + getAttack() + " attack, " + getDefense() + " defense)";
	}

}
