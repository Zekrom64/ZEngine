package com.zekrom_64.mathlib.tuple;

public interface VectorDouble extends VectorNumeric<Double> {

	@Override
	public default Class<?> type() { return double.class; }
	
	@Override
	public default Double get(int i) { return Double.valueOf(getDouble(i)); }
	
	@Override
	public default void set(int i, Double val) { setDouble(i, val); }
	
}
