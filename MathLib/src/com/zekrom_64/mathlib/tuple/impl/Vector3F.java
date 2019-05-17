package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleUnaryOperator;

public class Vector3F extends Vector2F {

	public float z;
	
	public Vector3F() {}
	
	public Vector3F(Vector3F v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Vector3F(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3F(float[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public float getFloat(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		case 2: return z;
		}
		return 0;
	}

	@Override
	public void setFloat(int i, float val) {
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
	public float lengthf() {
		return (float)Math.sqrt(x*x + y*y + z*z);
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
		x = (float)op.applyAsDouble(x);
		y = (float)op.applyAsDouble(y);
		z = (float)op.applyAsDouble(z);
	}

}
