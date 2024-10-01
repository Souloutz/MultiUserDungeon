package multiuserdungeon.map;

public interface TileObject {

	String getName();

	Tile getTile();

	boolean passable();

	boolean isTrap();
    
}
