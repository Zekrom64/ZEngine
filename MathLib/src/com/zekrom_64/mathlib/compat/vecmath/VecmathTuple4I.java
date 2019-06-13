package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple4i;

import com.zekrom_64.mathlib.tuple.Vector4;
import com.zekrom_64.mathlib.tuple.VectorInt;

public class VecmathTuple4I<T extends Tuple4i> implements Vector4<Integer>, VectorInt {
	
	public final T vector;
	
	public VecmathTuple4I(T vec) {
		vector = vec;
	}
	
	@Override
	public int getInt(int i) {
		switch(i) {
		case 0: return vector.x;
		case 1: return vector.y;
		case 2: return vector.z;
		case 3: return vector.w;
		default: return 0;
		}
	}

	@Override
	public void setInt(int i, int val) {
		switch(i) {
		case 0: vector.x = val; break;
		case 1: vector.y = val; break;
		case 2: vector.z = val; break;
		case 3: vector.w = val; break;
		}
	}

}
