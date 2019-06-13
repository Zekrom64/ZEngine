package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Matrix4d;

import com.zekrom_64.mathlib.matrix.Matrix4x4;
import com.zekrom_64.mathlib.matrix.MatrixDouble;

/** Compatibility class wrapping a Vecmath {@link javax.vecmath.Matrix4f Matrix4f}.
 * 
 * @author Zekrom_64
 *
 */
public class VecmathMatrix4x4D implements Matrix4x4<Double>, MatrixDouble {

	/** The matrix reference. */
	public final Matrix4d matrix;
	
	/** Creates a matrix wrapper with a new matrix instance. */
	public VecmathMatrix4x4D() {
		matrix = new Matrix4d();
	}
	
	/** Creates a matrix wrapper with a given matrix instance.
	 * 
	 * @param mat Matrix instance
	 */
	public VecmathMatrix4x4D(Matrix4d mat) {
		matrix = mat;
	}
	
	@Override
	public double getDouble(int row, int column) {
		return matrix.getElement(row, column);
	}

	@Override
	public void setDouble(int row, int column, double val) {
		matrix.setElement(row, column, val);
	}

}
