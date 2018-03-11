package com.zekrom_64.mathlib.tuple.impl;

import com.zekrom_64.mathlib.tuple.VectorDouble;
import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class VectorArrayDouble implements VectorDouble, VectorNumeric<Double> {

	public final double[] values;
	
	public VectorArrayDouble(double ... initial) {
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
		values[i] = val;
	}

}
