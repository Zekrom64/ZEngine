package com.zekrom_64.mathlib.tuple;

public interface Vector<T> {

	public Class<?> type();
	
	public int size();
	
	public T get(int i);
	
	public void set(int i, T val);
	
}
