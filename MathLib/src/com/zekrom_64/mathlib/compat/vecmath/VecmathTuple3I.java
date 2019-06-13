package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple3i;

import com.zekrom_64.mathlib.tuple.Vector3;
import com.zekrom_64.mathlib.tuple.VectorInt;

public class VecmathTuple3I<T extends Tuple3i> implements Vector3<Integer>, VectorInt {
	
	public final T tuple;
	
	public VecmathTuple3I(T tup) {
		tuple = tup;
	}
	
	@Override
	public int getInt(int i) {
		switch(i) {
		case 0: return tuple.x;
		case 1: return tuple.y;
		case 2: return tuple.z;
		default: return 0;
		}
	}

	@Override
	public void setInt(int i, int val) {
		switch(i) {
		case 0: tuple.x = val; break;
		case 1: tuple.y = val; break;
		case 2: tuple.z = val; break;
		}
	}

}
