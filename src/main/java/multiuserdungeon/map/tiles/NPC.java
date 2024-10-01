package multiuserdungeon.map.tiles;

import multiuserdungeon.map.Tile;

public class NPC extends Character {

	public NPC(String name, String description, boolean nocturnal) {
		super(name, description, 0, 0, 0);
	}

	@Override
	int getHealth() {
		return 0;
	}

	@Override
	int getDefense() {
		return 0;
	}

	@Override
	int getAttack() {
		return 0;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public Tile getTile() {
		return null;
	}

	@Override
	public void setTile(Tile tile) {

	}

	@Override
	public boolean passable() {
		return false;
	}

	@Override
	public boolean isTrap() {
		return false;
	}

}
