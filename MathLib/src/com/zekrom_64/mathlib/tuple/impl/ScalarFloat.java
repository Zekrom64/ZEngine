package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import com.zekrom_64.mathlib.tuple.ScalarNumeric;
import com.zekrom_64.mathlib.tuple.VectorFloat;
import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class ScalarFloat implements ScalarNumeric<Float>, VectorFloat {

	public float x;
	
	public ScalarFloat() {}
	
	public ScalarFloat(float value) { x = value; }

	@Override
	public double getDouble(int i) {
		return getFloat(i);
	}

	@Override
	public void setDouble(int i, double val) {
		setFloat(i, (float)val);
	}

	@Override
	public float getFloat(int i) {
		return x;
	}

	@Override
	public void setFloat(int i, float val) {
		x = val;
	}
	
	@Override
	public void set(ScalarNumeric<?> other) {
		x = (float)other.getDouble();
	}
	
	public void set(ScalarFloat other) {
		x = other.x;
	}

	@Override
	public double getDouble() {
		return x;
	}

	@Override
	public void setDouble(double val) {
		x = (float)val;
	}
	
	@Override
	public float lengthf() {
		return x;
	}
	
	@Override
	public void negate() { x = -x; }
	
	@Override
	public void add(double val) { x += val; }
	
	public void add(float val) { x += val; }
	
	@Override
	public void sub(double val) { x -= val; }
	
	public void sub(float val) { x -= val; }
	
	@Override
	public void mul(double val) { x *= val; }
	
	public void mul(float val) { x *= val; }
	
	@Override
	public void div(double val) { x /= val; }
	
	public void div(float val) { x /= val; }
	
	@Override
	public void mod(double val) { x %= val; }
	
	public void mod(float val) { x %= val; }
	
	@Override
	public void unary(DoubleUnaryOperator op) { x = (float)op.applyAsDouble(x); }
	
	@Override
	public void binary(DoubleBinaryOperator op, VectorNumeric<?> vec, boolean toMax) { x = (float)op.applyAsDouble(x, vec.getDouble(0)); }
	
	@Override
	public void binaryReverse(DoubleBinaryOperator op, VectorNumeric<?> vec, boolean toMax) { x = (float)op.applyAsDouble(vec.getDouble(0), x); }

}
