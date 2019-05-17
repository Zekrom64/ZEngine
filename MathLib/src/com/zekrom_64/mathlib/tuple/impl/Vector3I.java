package com.zekrom_64.mathlib.tuple.impl;

import com.zekrom_64.mathlib.tuple.Vector3;

public class Vector3I extends Vector2I implements Vector3<Integer> {

	public int z;
	
	public Vector3I() {}
	
	public Vector3I(Vector3I v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}
	
	public Vector3I(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3I(int[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
	}
	
	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int size() {
		return 3;
	}

	@Override
	public int getInt(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		case 2: return z;
		}
		return 0;
	}

	@Override
	public void setInt(int i, int val) {
		switch(i) {
		case 0: x = val; break;
		case 1: y = val; break;
		case 2: z = val; break;
		}
	}

}
