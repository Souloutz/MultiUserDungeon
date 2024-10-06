package multiuserdungeon.map.tiles;

import multiuserdungeon.Game;
import multiuserdungeon.clock.Night;
import multiuserdungeon.map.Tile;

public class NPC extends Character {

	private boolean nocturnal;
	private int nightBonus;

	public NPC(String name, String description, boolean nocturnal, int nightBonus) {
		super(name, description, 0, 0, 0);
		this.nocturnal = nocturnal;
		this.nightBonus = nightBonus;
	}

	@Override
	int getHealth() {
		return health;
	}

	@Override
	int getDefense() {
		return defense;
	}

	@Override
	int getAttack() {
		if (Game.getInstance().getTime() instanceof Night && nocturnal) {
			return attack + nightBonus;
		}
		return attack;
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
