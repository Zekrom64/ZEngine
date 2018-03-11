package com.zekrom_64.mathlib.matrix;

public interface MatrixFloat extends MatrixNumeric<Float> {

	@Override
	public default Float get(int row, int column) {
		return Float.valueOf(getFloat(row, column));
	}
	
	@Override
	public default void set(int row, int column, Float val) {
		setFloat(row, column, val);
	}
	
	@Override
	public default double getDouble(int row, int column) {
		return getFloat(row, column);
	}
	
	@Override
	public default void setDouble(int row, int column, double val) {
		setFloat(row, column, (float)val);
	}
	
	public float getFloat(int row, int column);
	
	public void setFloat(int row, int column, float val);
	
	@Override
	public default Class<?> type() {
		return float.class;
	}
	
}
