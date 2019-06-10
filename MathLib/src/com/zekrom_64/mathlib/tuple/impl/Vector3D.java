package com.zekrom_64.mathlib.tuple.impl;

import java.util.function.DoubleUnaryOperator;

public class Vector3D extends Vector2D {

	public double z;
	
	public Vector3D() {}
	
	public Vector3D(Vector3D v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(double[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
	}
	
	public void set(Vector3D v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	public boolean equals(Vector3D other) {
		return
				x == other.x &&
				y == other.y &&
				z == other.z;
	}

}
