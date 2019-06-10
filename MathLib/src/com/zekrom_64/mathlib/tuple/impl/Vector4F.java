package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleUnaryOperator;

public class Vector4F extends Vector3F {

	public float w;
	
	public Vector4F() {}
	
	public Vector4F(Vector4F v) {
		x = v.x;
		y = v.y;
		z = v.z;
		w = v.w;
	}
	
	public Vector4F(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4F(float[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
		w = values[3];
	}
	
	public void set(Vector4F v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.w = v.w;
	}
	
	public void set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public float getFloat(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		case 2: return z;
		case 3: return w;
		}
		return 0;
	}

	@Override
	public void setFloat(int i, float val) {
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
	public float lengthf() {
		return (float)Math.sqrt(x*x + y*y + z*z + w*w);
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
		x = (float)op.applyAsDouble(x);
		y = (float)op.applyAsDouble(y);
		z = (float)op.applyAsDouble(z);
		w = (float)op.applyAsDouble(w);
	}
	
	public boolean equals(Vector4F other) {
		return
				x == other.x &&
				y == other.y &&
				z == other.z &&
				w == other.w;
	}

}
