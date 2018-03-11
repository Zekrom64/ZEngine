package com.zekrom_64.mathlib.matrix;

public interface Matrix2x2<T> extends Matrix<T> {

	@Override
	public default int rows() { return 2; }
	
	@Override
	public default int columns() { return 2; }
	
}
