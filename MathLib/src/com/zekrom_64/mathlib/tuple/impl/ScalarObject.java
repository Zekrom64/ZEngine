package com.zekrom_64.mathlib.tuple.impl;

import com.zekrom_64.mathlib.tuple.Scalar;

public class ScalarObject<T> implements Scalar<T> {
	
	public T value;
	
	public ScalarObject() {}
	
	public ScalarObject(T value) { this.value = value; }

	@Override
	public Class<?> type() {
		return value == null ? null : value.getClass();
	}

	@Override
	public T get(int i) {
		return value;
	}

	@Override
	public void set(int i, T val) {
		value = val;
	}
	
}
