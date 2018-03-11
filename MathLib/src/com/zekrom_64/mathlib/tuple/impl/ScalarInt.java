package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import com.zekrom_64.mathlib.tuple.ScalarBinary;
import com.zekrom_64.mathlib.tuple.ScalarNumeric;
import com.zekrom_64.mathlib.tuple.VectorInt;
import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class ScalarInt implements ScalarBinary<Integer>, VectorInt {

	public int x;
	
	public ScalarInt() {}
	
	public ScalarInt(int value) { x = value; }

	@Override
	public double getDouble(int i) {
		return getInt(i);
	}

	@Override
	public void setDouble(int i, double val) {
		setInt(i, (int)val);
	}

	@Override
	public int getInt(int i) {
		return x;
	}

	@Override
	public void setInt(int i, int val) {
		x = val;
	}
	
	@Override
	public int getInt() {
		return x;
	}
	
	@Override
	public void setInt(int v) {
		x = v;
	}
	
	@Override
	public void set(ScalarNumeric<?> other) {
		x = (int)other.getDouble();
	}
	
	public void set(ScalarInt other) {
		x = other.x;
	}

	@Override
	public double getDouble() {
		return x;
	}

	@Override
	public void setDouble(double val) {
		x = (int)val;
	}
	
	@Override
	public double length() {
		return x;
	}
	
	@Override
	public void negate() { x = -x; }
	
	@Override
	public void add(double val) { x += val; }
	
	public void add(int val) { x += val; }
	
	@Override
	public void sub(double val) { x -= val; }
	
	public void sub(int val) { x -= val; }
	
	@Override
	public void mul(double val) { x *= val; }
	
	public void mul(int val) { x *= val; }
	
	@Override
	public void div(double val) { x /= val; }
	
	public void div(int val) { x /= val; }
	
	@Override
	public void mod(double val) { x %= val; }
	
	public void mod(int val) { x %= val; }
	
	@Override
	public void unary(DoubleUnaryOperator op) { x = (int)op.applyAsDouble(x); }
	
	@Override
	public void binary(DoubleBinaryOperator op, VectorNumeric<?> vec, boolean toMax) { x = (int)op.applyAsDouble(x, vec.getDouble(0)); }
	
	@Override
	public void binaryReverse(DoubleBinaryOperator op, VectorNumeric<?> vec, boolean toMax) { x = (int)op.applyAsDouble(vec.getDouble(0), x); }

}
