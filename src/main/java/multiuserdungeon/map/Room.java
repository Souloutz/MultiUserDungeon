package multiuserdungeon.map;

import java.util.Collection;
import java.util.Map;

public class Room {

	public Room(int width, int length) {

	}

	public Tile getTile(int x, int y) {
		return null;
	}

	public int getWidth() {
		return 0;
	}

	public int getLength() {
		return 0;
	}

	public void populate(Collection<Tile> tiles) {

	}

	public void handleExitRoom(Compass direction) {

	}

	public Tile getPlayerTile() {
		return null;
	}

	public void setPlayerTile() {

	}

	public Map<Tile, Room> getConnections() {
		return null;
	}

	@Override
	public boolean equals(Object otherRoom) {
		if (!(otherRoom instanceof Room))
			return false;

		// TODO
		return true;
	}

	@Override
	public String toString() {
		return "";
	}
}
