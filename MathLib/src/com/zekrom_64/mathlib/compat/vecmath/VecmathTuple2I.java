package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple2i;

import com.zekrom_64.mathlib.tuple.Vector2;
import com.zekrom_64.mathlib.tuple.VectorInt;

public class VecmathTuple2I<T extends Tuple2i> implements Vector2<Integer>, VectorInt {
	
	public final T tuple;
	
	public VecmathTuple2I(T tup) {
		tuple = tup;
	}
	
	@Override
	public int getInt(int i) {
		switch(i) {
		case 0: return tuple.x;
		case 1: return tuple.y;
		default: return 0;
		}
	}

	@Override
	public void setInt(int i, int val) {
		switch(i) {
		case 0: tuple.x = val; break;
		case 1: tuple.y = val; break;
		}
	}

}
