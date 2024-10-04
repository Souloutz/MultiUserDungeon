package multiuserdungeon.map;

public enum Compass {

	NORTH(0, 1),
	NORTH_EAST(1, 1),
	EAST(1, 0),
	SOUTH_EAST(1, -1),
	SOUTH(0, -1),
	SOUTH_WEST(-1, -1),
	WEST(-1, 0),
	NORTH_WEST(-1, 1);

	private final int x;
	private final int y;

	Compass(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Compass getOpposite() {
		for (Compass c : Compass.values()) {
			if (c.getX() == this.x * -1 && c.getY() == this.y * -1) {
				return c;
			}
		}
		return null;
	}

}
