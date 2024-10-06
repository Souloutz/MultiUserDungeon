package multiuserdungeon.map.tiles;

import multiuserdungeon.Game;
import multiuserdungeon.clock.CreatureBuff;

public class NPC extends Character {

	private final CreatureBuff creatureBuff;

	public NPC(String name, String description, CreatureBuff creatureBuff) {
		super(name, description, 0, 0, 0);
		this.creatureBuff = creatureBuff;
	}

	@Override
	public int getMaxHealth() {
		int health = super.getMaxHealth();
		health *= (int) Game.getInstance().getCurrentTime().getStatChange(this.creatureBuff);
		return health;
	}

	@Override
	public int getAttack() {
		int attack = super.getAttack();
		attack *= (int) Game.getInstance().getCurrentTime().getStatChange(this.creatureBuff);
		return attack;
	}

	@Override
	public int getDefense() {
		int defense = super.getDefense();
		defense *= (int) Game.getInstance().getCurrentTime().getStatChange(this.creatureBuff);
		return defense;
	}

}
