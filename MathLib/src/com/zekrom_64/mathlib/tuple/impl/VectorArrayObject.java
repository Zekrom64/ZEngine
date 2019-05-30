package com.zekrom_64.mathlib.tuple.impl;

import java.util.Arrays;

import com.zekrom_64.mathlib.tuple.Vector;

public class VectorArrayObject<T> implements Vector<T> {

	public final Class<?> valueType;
	public final T[] values;
	
	public VectorArrayObject(Class<T> clazz, @SuppressWarnings("unchecked") T ... initial) {
		values = initial;
		valueType = clazz;
	}
	
	@Override
	public Class<?> type() {
		return valueType;
	}

	@Override
	public int size() {
		return values.length;
	}

	@Override
	public T get(int i) {
		return values[i];
	}

	@Override
	public void set(int i, T val) {
		values[i] = val;
	}
	
	public boolean equals(VectorArrayObject<T> other) {
		return Arrays.equals(values, other.values);
	}

}
