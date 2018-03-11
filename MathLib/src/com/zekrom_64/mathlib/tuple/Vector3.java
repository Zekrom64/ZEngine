package com.zekrom_64.mathlib.tuple;

public interface Vector3<T> extends Vector<T> {

	@Override
	public default int size() { return 3; }
	
}
