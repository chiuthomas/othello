package com.fun;

public enum Direction {

	NORTH(0, -1), NE(1, -1), EAST(1, 0), SE(1, 1), SOUTH(0, 1), SW(-1, 1), WEST(-1, 0), NW(-1, -1);

	final int x;

	final int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public static Direction getDirection(int x, int y, int x1, int y1) {
		for (Direction direction : Direction.values()) {
			if (x + direction.x == x1 && y + direction.y == y1) {
				return direction;
			}
		}

		return null;
	}

}
