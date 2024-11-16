package multiuserdungeon.map.tiles;

import multiuserdungeon.Game;
import multiuserdungeon.clock.CreatureBuff;
import java.util.concurrent.ThreadLocalRandom;

public class NPC extends Character {

	private final CreatureBuff creatureBuff;

	public NPC(String name, String description, CreatureBuff creatureBuff) {
		super(name, description, ThreadLocalRandom.current().nextInt(50, 151), ThreadLocalRandom.current().nextInt(5, 16), ThreadLocalRandom.current().nextInt(0, 11));
		this.creatureBuff = creatureBuff;
	}

	public NPC(NPC npc){
		super(npc.getName(), npc.getDescription(), npc.getMaxHealth(), npc.getAttack(), npc.getDefense());
		this.creatureBuff = npc.getCreatureBuff();
		this.setHealth(npc.getHealth());
	}

	@Override
	public int getAttack() {
		int attack = super.getAttack();
		attack = (int) (attack * Game.getInstance().getCurrentTime().getStatChange(this.creatureBuff));
		return attack;
	}

	@Override
	public int getDefense() {
		int defense = super.getDefense();
		defense = (int) (defense * Game.getInstance().getCurrentTime().getStatChange(this.creatureBuff));
		return defense;
	}

	@Override
	public char getASCII() {
		return 'N';
	}

	public CreatureBuff getCreatureBuff() {
		return creatureBuff;
	}

}
