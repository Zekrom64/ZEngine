package com.zekrom_64.mathlib.tuple.impl;

import com.zekrom_64.mathlib.tuple.VectorFloat;
import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class VectorArrayFloat implements VectorFloat, VectorNumeric<Float> {

	public final float[] values;
	
	public VectorArrayFloat(float ... initial) {
		values = initial;
	}
	
	@Override
	public int size() {
		return values.length;
	}

	@Override
	public double getDouble(int i) {
		return values[i];
	}

	@Override
	public void setDouble(int i, double val) {
		values[i] = (float)val;
	}

	@Override
	public float getFloat(int i) {
		return values[i];
	}

	@Override
	public void setFloat(int i, float val) {
		values[i] = val;
	}

}
