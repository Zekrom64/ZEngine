package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleUnaryOperator;

import com.zekrom_64.mathlib.tuple.VectorNumeric;

public class Vector2F extends ScalarFloat {
	
	public float y;
	
	public Vector2F() {}
	
	public Vector2F(Vector2F v) {
		x = v.x;
		y = v.y;
	}
	
	public Vector2F(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2F(float[] values) {
		x = values[0];
		y = values[1];
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public float getFloat(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		}
		return 0;
	}

	@Override
	public void setFloat(int i, float val) {
		switch(i) {
		case 0: x = val; break;
		case 1: y = val; break;
		}
	}
	
	@Override
	public int size() {
		return 2;
	}
	
	@Override
	public double length() {
		return Math.sqrt(x*x + y*y);
	}
	
	@Override
	public float lengthf() {
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public void add(Vector2F vec) {
		x += vec.x;
		y += vec.y;
	}
	
	public void sub(Vector2F vec) {
		x -= vec.x;
		y -= vec.y;
	}
	
	public void mul(Vector2F vec) {
		x *= vec.x;
		y *= vec.y;
	}
	
	public void div(Vector2F vec) {
		x /= vec.x;
		y /= vec.y;
	}
	
	public void mod(Vector2F vec) {
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
		x = (float)op.applyAsDouble(x);
		y = (float)op.applyAsDouble(y);
	}

}
