package com.zekrom_64.mathlib.tuple.impl;

import com.zekrom_64.mathlib.tuple.VectorInt;
import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class VectorArrayInt implements VectorInt, VectorNumeric<Integer> {

	public final int[] values;
	
	public VectorArrayInt(int ... initial) {
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
		values[i] = (int)val;
	}

	@Override
	public int getInt(int i) {
		return values[i];
	}

	@Override
	public void setInt(int i, int val) {
		values[i] = val;
	}

}
