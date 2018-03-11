package com.zekrom_64.ze.voxel;

/** Enumeration for the 6 different directions.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEDirection {
	
	/** Upward */
	UP(0, 1, 0),
	/** Downwards */
	DOWN(0, -1, 0),
	/** North */
	NORTH(0, 0, 1),
	/** South */
	SOUTH(0, 0, -1),
	/** East */
	EAST(1, 0, 0),
	/** West */
	WEST(-1, 0, 0);
	
	/** Faster way of accessing all the values */
	public static final ZEDirection[] values = new ZEDirection[] {UP, DOWN, NORTH, SOUTH, EAST, WEST};
	
	/** Normals for this direction */
	public final int x, y, z;
	
	/** Axis the direction is on */
	public final ZEAxis axis;
	
	private ZEDirection(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		if (x != 0) axis = ZEAxis.X;
		else if (y != 0) axis = ZEAxis.Y;
		else axis = ZEAxis.Z;
	}
	
	private static final int[] opposite = new int[] {1, 0, 3, 2, 5, 4};
	
	/** Gets the opposite of this direction.
	 * 
	 * @return The opposite direction
	 */
	public ZEDirection opposite() {
		return values[opposite[ordinal()]];
		// This will never be called, because this will always be one of the above
	}
	
	private static final ZEDirection[][] rotationMatrix = new ZEDirection[][] {
			new ZEDirection[] {UP, UP, EAST, WEST, SOUTH, NORTH},
			new ZEDirection[] {DOWN, DOWN, WEST, EAST, NORTH, SOUTH},
			new ZEDirection[] {EAST, WEST, NORTH, NORTH, UP, DOWN},
			new ZEDirection[] {WEST, EAST, SOUTH, SOUTH, DOWN, UP},
			new ZEDirection[] {NORTH, SOUTH, DOWN, UP, EAST, EAST},
			new ZEDirection[] {SOUTH, NORTH, UP, DOWN, WEST, WEST}
	};
	
	private static final ZEDirection[][] clockwise = new ZEDirection[][] {
			new ZEDirection[] {SOUTH, UP, EAST},
			new ZEDirection[] {NORTH, DOWN, WEST},
			new ZEDirection[] {UP, EAST, NORTH},
			new ZEDirection[] {DOWN, WEST, SOUTH},
			new ZEDirection[] {EAST, SOUTH, DOWN},
			new ZEDirection[] {WEST, NORTH, UP}
	};
	
	/** Rotates this direction clockwise about a vector in the given direction.
	 * 
	 * @param around Rotation vector
	 * @return Result of rotation
	 */
	public ZEDirection rotate(ZEDirection around) {
		return rotationMatrix[ordinal()][around.ordinal()];
	}
	
	/** Rotates clockwise about the given axis.
	 * 
	 * @param about Axis to rotate about
	 * @return Result of rotation
	 */
	public ZEDirection rotateClockwise(ZEAxis about) {
		return clockwise[ordinal()][about.ordinal()];
	}
	
	/** Rotates counter-clockwise about the given axis.
	 * 
	 * @param about Axis to rotate about
	 * @return Result of rotation
	 */
	public ZEDirection rotateCounterclockwise(ZEAxis about) {
		if (axis != about) return clockwise[ordinal()][about.ordinal()];
		else return this;
	}
	
}
