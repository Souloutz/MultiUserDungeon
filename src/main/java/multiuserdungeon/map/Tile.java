package multiuserdungeon.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import multiuserdungeon.Game;
import multiuserdungeon.map.tiles.*;
import multiuserdungeon.map.tiles.shrine.Shrine;
import multiuserdungeon.map.tiles.trap.Trap;

public class Tile {

	private final int row;
	private final int col;
	private final LinkedList<TileObject> objects;
	private Map<Compass, Tile> adjacent;

	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		this.objects = new LinkedList<>();
		this.adjacent = null;
	}

	public Tile(Tile tile) {
		this.row = tile.getRow();
		this.col = tile.getCol();
		this.objects = new LinkedList<>();
		for(TileObject tileObject : tile.getObjects()) {
			TileObject newTileObject = null;

			if(tileObject instanceof Corpse corpse) {
				newTileObject = new Corpse(corpse);
			} else if(tileObject instanceof Chest chest) {
				newTileObject = new Chest(chest);
			} else if(tileObject instanceof NPC npc) {
				newTileObject = new NPC(npc);
			} else if(tileObject instanceof Obstacle obstacle) {
				newTileObject = new Obstacle(obstacle);
			} else if(tileObject instanceof Player player) {
				if(player.getName().equals(Game.getInstance().getPlayer().getName())) {
					continue;
				}
				newTileObject = new Player(player);
			} else if(tileObject instanceof Shrine shrine) {
				newTileObject = new Shrine(shrine);
			} else if(tileObject instanceof Trap trap) {
				newTileObject = new Trap(trap);
			} else if(tileObject instanceof Merchant merchant) {
				newTileObject = new Merchant(merchant);
			}

			if(newTileObject != null) {
				newTileObject.setTile(this);
				this.objects.add(newTileObject);
			}
		}
		this.adjacent = null;
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public Tile getTile(Compass compass) {
		return this.adjacent.get(compass);
	}

	public List<TileObject> getObjects() {
		return this.objects;
	}

	public void addObject(TileObject object) {
		this.objects.add(object);
	}

	public void removeObject(TileObject object) {
		this.objects.remove(object);
	}

	public boolean passable() {
		for(TileObject object : this.objects) {
			if(!object.passable()) {
				return false;
			}
		}

		return true;
	}

	public Map<Compass, Tile> getAdjacent() {
		return this.adjacent;
	}

	public void setAdjacent(Map<Compass, Tile> adjacent) {
		this.adjacent = adjacent;
	}

	public char getASCII() {
		try {
			TileObject to = this.objects.getLast();
			return to.getASCII();
		} catch (NoSuchElementException e) {
			return '-';
		}
		
	}

	public Player getPlayer() {
		for (TileObject object : objects) {
			if (object instanceof Player player) {
				return player;
			}
		}

		return null;
	}

	public NPC getNPC() {
		for (TileObject object : objects) {
			if (object instanceof NPC npc) {
				return npc;
			}
		}

		return null;
	}

	public Trap getTrap() {
		for (TileObject object : objects) {
			if (object instanceof Trap trap) {
				return trap;
			}
		}

		return null;
	}

	public Chest getChest() {
		for(TileObject object : objects) {
			if(object instanceof Chest chest) {
				return chest;
			}
		}

		return null;
	}

	public Shrine getShrine() {
		for (TileObject object: objects) {
			if (object instanceof Shrine shrine) {
				return shrine;
			}
		}
		return null;
	}

	public Merchant getMerchant() {
		for (TileObject object: objects) {
			if (object instanceof Merchant merchant) {
				return merchant;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		try {
			TileObject to = this.objects.getLast();
			return to.toString();
		} catch(NoSuchElementException e) {
			return "";
		}
	}

}