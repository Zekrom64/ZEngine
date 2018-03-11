package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import com.zekrom_64.mathlib.tuple.ScalarNumeric;
import com.zekrom_64.mathlib.tuple.VectorDouble;
import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class ScalarDouble implements ScalarNumeric<Double>, VectorDouble {

	public double x;
	
	public ScalarDouble() {}
	
	public ScalarDouble(double value) { x = value; }

	@Override
	public double getDouble(int i) {
		return x;
	}

	@Override
	public void setDouble(int i, double val) {
		x = val;
	}

	@Override
	public double length() {
		return x;
	}
	
	@Override
	public void set(ScalarNumeric<?> other) {
		x = other.getDouble();
	}
	
	public void set(ScalarDouble other) {
		x = other.x;
	}

	@Override
	public double getDouble() {
		return x;
	}

	@Override
	public void setDouble(double val) {
		x = val;
	}

	@Override
	public void negate() {
		x = -x;
	}

	@Override
	public void add(double val) {
		x += val;
	}

	@Override
	public void sub(double val) {
		x -= val;
	}

	@Override
	public void mul(double val) {
		x *= val;
	}

	@Override
	public void div(double val) {
		x /= val;
	}

	@Override
	public void mod(double val) {
		x %= val;
	}

	@Override
	public void unary(DoubleUnaryOperator op) {
		x = op.applyAsDouble(x);
	}

	@Override
	public void binary(DoubleBinaryOperator op, VectorNumeric<?> other, boolean toMax) {
		x = op.applyAsDouble(x, other.getDouble(0));
	}

	@Override
	public void binaryReverse(DoubleBinaryOperator op, VectorNumeric<?> other, boolean toMax) {
		x = op.applyAsDouble(other.getDouble(0), x);
	}

}
