package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple3f;

import com.zekrom_64.mathlib.tuple.Vector3;
import com.zekrom_64.mathlib.tuple.VectorFloat;

public class VecmathTuple3F<T extends Tuple3f> implements Vector3<Float>, VectorFloat {
	
	public final T tuple;
	
	public VecmathTuple3F(T tup) {
		tuple = tup;
	}
	
	@Override
	public float getFloat(int i) {
		switch(i) {
		case 0: return tuple.x;
		case 1: return tuple.y;
		case 2: return tuple.z;
		default: return 0;
		}
	}

	@Override
	public void setFloat(int i, float val) {
		switch(i) {
		case 0: tuple.x = val; break;
		case 1: tuple.y = val; break;
		case 2: tuple.z = val; break;
		}
	}

}
