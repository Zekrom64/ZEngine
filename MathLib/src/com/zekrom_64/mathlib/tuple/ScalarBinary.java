package com.zekrom_64.mathlib.tuple;

public interface ScalarBinary<T extends Number> extends ScalarNumeric<T>, VectorBinary<T> {

	public int getInt();
	
	public void setInt(int val);
	
	public default void invert() { setInt(~getInt()); }
	
	public default void and(int scalar) { setInt(getInt() & scalar); }
	
	public default void or(int scalar) { setInt(getInt() | scalar); }
	
	public default void xor(int scalar) { setInt(getInt() ^ scalar); }
	
	public default void assignBit(int bit, boolean value) {
		int invmask = 1 << bit;
		int mask = ~invmask;
		int val = getInt() & mask;
		if (value) val |= invmask;
		setInt(val);
	}
	
	public default void setBit(int bit) {
		setInt(getInt() | (1 << bit));
	}
	
	public default void resetBit(int bit) {
		setInt(getInt() & ~(1 << bit));
	}
	
	public default boolean testBit(int bit) {
		return (getInt() & (1 << bit)) != 0;
	}
	
}
