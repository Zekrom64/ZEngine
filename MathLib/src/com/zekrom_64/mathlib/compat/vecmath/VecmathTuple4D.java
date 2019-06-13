package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple4d;

import com.zekrom_64.mathlib.tuple.Vector4;
import com.zekrom_64.mathlib.tuple.VectorDouble;

public class VecmathTuple4D<T extends Tuple4d> implements Vector4<Double>, VectorDouble {
	
	public final T vector;
	
	public VecmathTuple4D(T vec) {
		vector = vec;
	}
	
	@Override
	public double getDouble(int i) {
		switch(i) {
		case 0: return vector.x;
		case 1: return vector.y;
		case 2: return vector.z;
		case 3: return vector.w;
		default: return 0;
		}
	}

	@Override
	public void setDouble(int i, double val) {
		switch(i) {
		case 0: vector.x = val; break;
		case 1: vector.y = val; break;
		case 2: vector.z = val; break;
		case 3: vector.w = val; break;
		}
	}

}
