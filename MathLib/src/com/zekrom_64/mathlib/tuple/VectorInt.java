package com.zekrom_64.mathlib.tuple;

public interface VectorInt extends VectorNumeric<Integer> {

	@Override
	public default Class<?> type() { return int.class; }
	
	@Override
	public default Integer get(int i) { return Integer.valueOf(getInt(i)); }
	
	@Override
	public default void set(int i, Integer val) { setInt(i, val); }
	
	public int getInt(int i);
	
	public void setInt(int i, int val);
	
}
