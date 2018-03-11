package com.zekrom_64.mathlib.tuple;

public interface Scalar<T> extends Vector<T> {

	@Override
	public default int size() { return 1; }
	
	public default T get() { return get(0); }
	
	public default void set(T t) { set(0, t); }
	
}
