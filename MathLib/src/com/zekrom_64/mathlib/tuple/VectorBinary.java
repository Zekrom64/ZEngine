package com.zekrom_64.mathlib.tuple;

public interface VectorBinary<T extends Number> extends VectorNumeric<T> {

	public int getInt(int i);
	
	public void setInt(int i, int val);
	
	public default void invert() {
		for(int i = 0; i < size(); i++) {
			setInt(i, ~getInt(i));
		}
	}
	
	public default void and(int scalar) {
		for(int i = 0; i < size(); i++) {
			setInt(i, getInt(i) & scalar);
		}
	}
	
	public default void and(ScalarBinary<?> scalar) {
		and(scalar.getInt());
	}
	
	public default void or(int scalar) {
		for(int i = 0; i < size(); i++) {
			setInt(i, getInt(i) | scalar);
		}
	}
	
	public default void or(ScalarBinary<?> scalar) {
		or(scalar.getInt());
	}
	
	public default void xor(int scalar) {
		for(int i = 0; i < size(); i++) {
			setInt(i, getInt(i) ^ scalar);
		}
	}
	
	public default void xor(ScalarBinary<?> scalar) {
		xor(scalar.getInt());
	}
	
}
