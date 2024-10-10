package multiuserdungeon.map;

public enum Compass {

	NORTH(-1, 0),
	NORTH_EAST(-1, 1),
	EAST(0, 1),
	SOUTH_EAST(1, 1),
	SOUTH(1, 0),
	SOUTH_WEST(1, -1),
	WEST(0, -1),
	NORTH_WEST(-1, -1);

	private final int rowOffset;
	private final int colOffset;

	Compass(int rowOffset, int colOffset) {
		this.rowOffset = rowOffset;
		this.colOffset = colOffset;
	}

	public int getRowOffset() {
		return this.rowOffset;
	}

	public int getColOffset() {
		return this.colOffset;
	}

	public Compass getOpposite() {
		for (Compass c : Compass.values()) {
			if (c.getRowOffset() == this.rowOffset * -1 && c.getColOffset() == this.colOffset * -1) {
				return c;
			}
		}
		return null;
	}

}
