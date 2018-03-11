package com.zekrom_64.mathlib.matrix;

public interface Matrix<T> {

	public Class<?> type();
	
	public int rows();
	
	public int columns();
	
	public T get(int row, int column);
	
	public void set(int row, int column, T val);
	
}
