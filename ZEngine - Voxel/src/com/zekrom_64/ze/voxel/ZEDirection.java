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
	private ZEDirection(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	private static final int[][] clockwise = new int[][] {
			new int[] {2, 0, 4},
			new int[] {3, 1, 5},
			new int[] {1, 4, 2},
			new int[] {0, 5, 3},
			new int[] {4, 3, 0},
			new int[] {5, 2, 1}
	};
	
	private static final int[][] counterclockwise = new int[][] {
		
	};
	
	public ZEDirection rotateClockwise(ZEAxis about) {
		return values[clockwise[ordinal()][about.ordinal()]];
	}
	
}
