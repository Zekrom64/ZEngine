package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleUnaryOperator;

import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class Vector2Double extends ScalarDouble {
	
	public double y;
	
	public Vector2Double() {}
	
	public Vector2Double(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2Double(double[] values) {
		x = values[0];
		y = values[1];
	}
	
	@Override
	public int size() {
		return 2;
	}

	@Override
	public double getDouble(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		}
		return 0;
	}

	@Override
	public void setDouble(int i, double val) {
		switch(i) {
		case 0: x = val; break;
		case 1: y = val; break;
		}
	}
	
	@Override
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	public void add(Vector2Double vec) {
		x += vec.x;
		y += vec.y;
	}
	
	public void sub(Vector2Double vec) {
		x -= vec.x;
		y -= vec.y;
	}
	
	public void mul(Vector2Double vec) {
		x *= vec.x;
		y *= vec.y;
	}
	
	public void div(Vector2Double vec) {
		x /= vec.x;
		y /= vec.y;
	}
	
	public void mod(Vector2Double vec) {
		x %= vec.x;
		y %= vec.y;
	}

	@Override
	public void add(VectorNumeric<?> tuple) {
		x += tuple.getDouble(0);
		y += tuple.getDouble(1);
	}

	@Override
	public void sub(VectorNumeric<?> tuple) {
		x -= tuple.getDouble(0);
		y -= tuple.getDouble(1);
	}

	@Override
	public void mul(VectorNumeric<?> tuple) {
		x *= tuple.getDouble(0);
		y *= tuple.getDouble(1);
	}

	@Override
	public void div(VectorNumeric<?> tuple) {
		x /= tuple.getDouble(0);
		y /= tuple.getDouble(1);
	}

	@Override
	public void mod(VectorNumeric<?> tuple) {
		x %= tuple.getDouble(0);
		y %= tuple.getDouble(1);
	}

	@Override
	public double dot(VectorNumeric<?> other) {
		return (x * other.getDouble(0)) + (y * other.getDouble(0));
	}

	@Override
	public void negate() {
		x = -x;
		y = -y;
	}
	
	@Override
	public void add(double val) {
		x += val;
		y += val;
	}
	
	@Override
	public void sub(double val) {
		x -= val;
		y -= val;
	}
	
	@Override
	public void mul(double val) {
		x *= val;
		y *= val;
	}
	
	@Override
	public void div(double val) {
		x /= val;
		y /= val;
	}
	
	@Override
	public void mod(double val) {
		x %= val;
		y %= val;
	}
	
	@Override
	public void unary(DoubleUnaryOperator op) {
		x = op.applyAsDouble(x);
		y = op.applyAsDouble(y);
	}

}
