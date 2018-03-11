package com.zekrom_64.mathlib.matrix;

public interface Matrix4x4<T> extends Matrix3x3<T> {

	@Override
	public default int rows() { return 4; }
	
	@Override
	public default int columns() { return 4; }
	
}
