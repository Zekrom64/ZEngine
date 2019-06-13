package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Tuple3d;

import com.zekrom_64.mathlib.tuple.Vector3;
import com.zekrom_64.mathlib.tuple.VectorDouble;

public class VecmathTuple3D<T extends Tuple3d> implements Vector3<Double>, VectorDouble {
	
	public final T tuple;
	
	public VecmathTuple3D(T tup) {
		tuple = tup;
	}
	
	@Override
	public double getDouble(int i) {
		switch(i) {
		case 0: return tuple.x;
		case 1: return tuple.y;
		case 2: return tuple.z;
		default: return 0;
		}
	}

	@Override
	public void setDouble(int i, double val) {
		switch(i) {
		case 0: tuple.x = val; break;
		case 1: tuple.y = val; break;
		case 2: tuple.z = val; break;
		}
	}

}
