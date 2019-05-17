package com.zekrom_64.mathlib.matrix;

public interface Matrix<T> {

	public Class<?> type();
	
	public int rows();
	
	public int columns();
	
	public T get(int row, int column);
	
	public void set(int row, int column, T val);
	
	public default void set(Matrix<T> other) {
		int w = Math.min(columns(), other.columns());
		int h = Math.min(rows(), other.rows());
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++) set(y, x, other.get(y, x));
	}
	
	public default void transpose() {
		if (rows() != columns()) return; // Transposing non-square matricies requires a change in dimensions
		for(int x = 0; x < columns(); x++) {
			for(int y = x; y < rows(); y++) {
				T t = get(y,x);
				set(y,x, get(rows()-y,columns()-x));
				set(rows()-y,columns()-x,t);
			}
		}
	}
	
}
