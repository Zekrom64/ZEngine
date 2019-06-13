package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple2d;

import com.zekrom_64.mathlib.tuple.Vector2;
import com.zekrom_64.mathlib.tuple.VectorDouble;

public class VecmathTuple2D<T extends Tuple2d> implements Vector2<Double>, VectorDouble {
	
	public final T tuple;
	
	public VecmathTuple2D(T tup) {
		tuple = tup;
	}
	
	@Override
	public double getDouble(int i) {
		switch(i) {
		case 0: return tuple.x;
		case 1: return tuple.y;
		default: return 0;
		}
	}

	@Override
	public void setDouble(int i, double val) {
		switch(i) {
		case 0: tuple.x = val; break;
		case 1: tuple.y = val; break;
		}
	}

}
