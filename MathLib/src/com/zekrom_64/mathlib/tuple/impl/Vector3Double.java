package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleUnaryOperator;

public class Vector3Double extends Vector2Double {

	public double z;
	
	public Vector3Double() {}
	
	public Vector3Double(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3Double(double[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
	}

	@Override
	public double getDouble(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		case 2: return z;
		}
		return 0;
	}

	@Override
	public void setDouble(int i, double val) {
		switch(i) {
		case 0: x = val; break;
		case 1: y = val; break;
		case 2: z = val; break;
		}
	}
	
	@Override
	public int size() {
		return 3;
	}
	
	@Override
	public double length() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	@Override
	public void negate() {
		x = -x;
		y = -y;
		z = -z;
	}
	
	@Override
	public void add(double val) {
		x += val;
		y += val;
		z += val;
	}
	
	@Override
	public void sub(double val) {
		x -= val;
		y -= val;
		z -= val;
	}
	
	@Override
	public void mul(double val) {
		x *= val;
		y *= val;
		z *= val;
	}
	
	@Override
	public void div(double val) {
		x /= val;
		y /= val;
		z /= val;
	}
	
	@Override
	public void mod(double val) {
		x %= val;
		y %= val;
		z %= val;
	}
	
	@Override
	public void unary(DoubleUnaryOperator op) {
		x = op.applyAsDouble(x);
		y = op.applyAsDouble(y);
		z = op.applyAsDouble(z);
	}

}
