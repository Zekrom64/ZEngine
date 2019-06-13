package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Matrix3d;

import com.zekrom_64.mathlib.matrix.Matrix3x3;
import com.zekrom_64.mathlib.matrix.MatrixDouble;

/** Compatibility class wrapping a Vecmath {@link javax.vecmath.Matrix3f Matrix3f}.
 * 
 * @author Zekrom_64
 *
 */
public class VecmathMatrix3x3D implements Matrix3x3<Double>, MatrixDouble {

	/** The matrix reference. */
	public final Matrix3d matrix;
	
	/** Creates a matrix wrapper with a new matrix instance. */
	public VecmathMatrix3x3D() {
		matrix = new Matrix3d();
	}
	
	/** Creates a matrix wrapper with a given matrix instance.
	 * 
	 * @param mat Matrix instance
	 */
	public VecmathMatrix3x3D(Matrix3d mat) {
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
