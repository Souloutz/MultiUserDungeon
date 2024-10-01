package multiuserdungeon.map;

public interface TileObject {

	String getName();

	Tile getTile();

	void setTile(Tile tile);

	boolean passable();

	boolean isTrap();
    
}
