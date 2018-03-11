package com.zekrom_64.mathlib.matrix;

public interface MatrixDouble extends MatrixNumeric<Double> {

	@Override
	public default Double get(int row, int column) {
		return get(row, column);
	}
	
	@Override
	public default void set(int row, int column, Double val) {
		set(row, column, val);
	}
	
	@Override
	public default Class<?> type() {
		return double.class;
	}
	
}
