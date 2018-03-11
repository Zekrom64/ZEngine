package com.zekrom_64.mathlib.tuple;

public interface VectorFloat extends VectorNumeric<Float> {

	@Override
	public default Class<?> type() { return float.class; }
	
	@Override
	public default Float get(int i) { return Float.valueOf(getFloat(i)); }
	
	@Override
	public default void set(int i, Float val) { setFloat(i, val); }
	
	public float getFloat(int i);
	
	public void setFloat(int i, float val);
	
	public default float lengthf() {
		float len = 0;
		for(int i = 0; i < size(); i++) {
			float axis = getFloat(i);
			len += axis * axis;
		}
		return (float)Math.sqrt(len);
	}
	
}
