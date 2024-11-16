package multiuserdungeon.map.tiles.shrine;

import multiuserdungeon.Game;
import multiuserdungeon.map.Tile;
import multiuserdungeon.map.TileObject;

public class Shrine implements TileObject {

    private final String name;
    private Snapshot snapshot;
    private Tile tile;

    public Shrine(String name){
        this.name = name;
		this.tile = null;
    }

	public Shrine(Shrine shrine){
		this.name = shrine.getName();
		this.snapshot = shrine.getSnapshot();
		this.tile = null;
	}

    public void storeSnapshot(){
        this.snapshot = Game.getInstance().createSnapshot();
    }
    
    public void restoreGame(){
        Game.getInstance().restoreGame(this.snapshot);
    }

    public Snapshot getSnapshot() {
        return snapshot;
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
		return true;
	}

	@Override
	public char getASCII() {
		return 'S';
	}

	@Override
	public String toString() {
		return "A " + this.name;
	}

    
}