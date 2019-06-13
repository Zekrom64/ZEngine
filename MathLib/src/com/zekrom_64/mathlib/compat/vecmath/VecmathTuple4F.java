package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple4f;

import com.zekrom_64.mathlib.tuple.Vector4;
import com.zekrom_64.mathlib.tuple.VectorFloat;

public class VecmathTuple4F<T extends Tuple4f> implements Vector4<Float>, VectorFloat {
	
	public final T vector;
	
	public VecmathTuple4F(T vec) {
		vector = vec;
	}
	
	@Override
	public float getFloat(int i) {
		switch(i) {
		case 0: return vector.x;
		case 1: return vector.y;
		case 2: return vector.z;
		case 3: return vector.w;
		default: return 0;
		}
	}

	@Override
	public void setFloat(int i, float val) {
		switch(i) {
		case 0: vector.x = val; break;
		case 1: vector.y = val; break;
		case 2: vector.z = val; break;
		case 3: vector.w = val; break;
		}
	}

}
