package com.zekrom_64.mathlib.tuple.impl;

import com.zekrom_64.mathlib.tuple.Vector4;

public class Vector4Int extends Vector3Int implements Vector4<Integer> {

	public int w;
	
	public Vector4Int() {}
	
	public Vector4Int(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4Int(int[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
		w = values[3];
	}
	
	@Override
	public int size() {
		return 4;
	}

	@Override
	public int getInt(int i) {
		switch(i) {
		case 0: return x;
		case 1: return y;
		case 2: return z;
		case 3: return w;
		}
		return 0;
	}

	@Override
	public void setInt(int i, int val) {
		switch(i) {
		case 0: x = val; break;
		case 1: y = val; break;
		case 2: z = val; break;
		case 3: w = val; break;
		}
	}
	
}
