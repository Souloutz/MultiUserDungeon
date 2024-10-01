package multiuserdungeon.map.tiles;

import multiuserdungeon.map.Compass;
import multiuserdungeon.map.TileObject;

public abstract class Character implements TileObject {

	public Character(String name, String description, int baseHealth, int baseAttack, int baseDefense) {

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
		return 0;
	}

	public int getBaseDefense() {
		return 0;
	}

	abstract int getHealth();

	abstract int getDefense();

	abstract int getAttack();

}
