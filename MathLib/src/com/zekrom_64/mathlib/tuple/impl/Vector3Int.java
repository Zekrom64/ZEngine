package com.zekrom_64.mathlib.tuple.impl;

import com.zekrom_64.mathlib.tuple.Vector3;

public class Vector3Int extends Vector2Int implements Vector3<Integer> {

	public int z;
	
	public Vector3Int() {}
	
	public Vector3Int(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3Int(int[] values) {
		x = values[0];
		y = values[1];
		z = values[2];
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
