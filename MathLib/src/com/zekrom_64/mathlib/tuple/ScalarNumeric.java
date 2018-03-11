package com.zekrom_64.mathlib.tuple;

public interface ScalarNumeric<T extends Number> extends Scalar<T>, VectorNumeric<T> {
	
	public default void set(ScalarNumeric<?> other) {
		int nElems = size();
		for(int i = 0; i < nElems; i++) setDouble(i, other.getDouble(i));
	}

	public default double getDouble() { return getDouble(0); }
	
	public default void setDouble(double val) { setDouble(0, val); }
	
	public default void negate() { setDouble(-getDouble()); }
	
	public default void add(double val) { setDouble(getDouble() + val); }
	
	public default void add(ScalarNumeric<?> val) { add(val.getDouble()); }
	
	public default void sub(double val) { setDouble(getDouble() - val); }
	
	public default void sub(ScalarNumeric<?> val) { sub(val.getDouble()); }
	
	public default void mul(double val) { setDouble(getDouble() * val); }
	
	public default void mul(ScalarNumeric<?> val) { mul(val.getDouble()); }
	
	public default void div(double val) { setDouble(getDouble() / val); }
	
	public default void div(ScalarNumeric<?> val) { div(val.getDouble()); }
	
	public default void mod(double val) { setDouble(getDouble() % val); }
	
	public default void mod(ScalarNumeric<?> val) { mod(val.getDouble()); }
	
}
