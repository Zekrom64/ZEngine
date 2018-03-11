package com.zekrom_64.mathlib.matrix;

public interface Matrix3x3<T> extends Matrix2x2<T> {

	@Override
	public default int rows() { return 3; }
	
	@Override
	public default int columns() { return 3; }
	
}
