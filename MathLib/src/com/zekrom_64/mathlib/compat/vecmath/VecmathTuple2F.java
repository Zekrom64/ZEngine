package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple2f;

import com.zekrom_64.mathlib.tuple.Vector2;
import com.zekrom_64.mathlib.tuple.VectorFloat;

public class VecmathTuple2F<T extends Tuple2f> implements Vector2<Float>, VectorFloat {
	
	public final T tuple;
	
	public VecmathTuple2F(T tup) {
		tuple = tup;
	}
	
	@Override
	public float getFloat(int i) {
		switch(i) {
		case 0: return tuple.x;
		case 1: return tuple.y;
		default: return 0;
		}
	}

	@Override
	public void setFloat(int i, float val) {
		switch(i) {
		case 0: tuple.x = val; break;
		case 1: tuple.y = val; break;
		}
	}

}
