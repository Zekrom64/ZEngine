package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleUnaryOperator;

public class Vector4Double extends Vector3Double {

	public double w;
	
	public Vector4Double() {}
	
	public Vector4Double(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4Double(double[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
		w = values[3];
	}

	@Override
	public double getDouble(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		case 2: return z;
		case 3: return w;
		}
		return 0;
	}

	@Override
	public void setDouble(int i, double val) {
		switch(i) {
		case 0: x = val; break;
		case 1: y = val; break;
		case 2: z = val; break;
		case 3: w = val; break;
		}
	}
	
	@Override
	public int size() {
		return 4;
	}
	
	@Override
	public double length() {
		return Math.sqrt(x*x + y*y + z*z + w*w);
	}
	
	@Override
	public void negate() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
	}
	
	@Override
	public void add(double val) {
		x += val;
		y += val;
		z += val;
		w += val;
	}
	
	@Override
	public void sub(double val) {
		x -= val;
		y -= val;
		z -= val;
		w -= val;
	}
	
	@Override
	public void mul(double val) {
		x *= val;
		y *= val;
		z *= val;
		w *= val;
	}
	
	@Override
	public void div(double val) {
		x /= val;
		y /= val;
		z /= val;
		w /= val;
	}
	
	@Override
	public void mod(double val) {
		x %= val;
		y %= val;
		z %= val;
		w %= val;
	}
	
	@Override
	public void unary(DoubleUnaryOperator op) {
		x = op.applyAsDouble(x);
		y = op.applyAsDouble(y);
		z = op.applyAsDouble(z);
		w = op.applyAsDouble(w);
	}

}
