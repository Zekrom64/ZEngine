package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Matrix3f;

import com.zekrom_64.mathlib.matrix.Matrix3x3;
import com.zekrom_64.mathlib.matrix.MatrixFloat;

/** Compatibility class wrapping a Vecmath {@link javax.vecmath.Matrix3f Matrix3f}.
 * 
 * @author Zekrom_64
 *
 */
public class VecmathMatrix3x3F implements Matrix3x3<Float>, MatrixFloat {

	/** The matrix reference. */
	public final Matrix3f matrix;
	
	/** Creates a matrix wrapper with a new matrix instance. */
	public VecmathMatrix3x3F() {
		matrix = new Matrix3f();
	}
	
	/** Creates a matrix wrapper with a given matrix instance.
	 * 
	 * @param mat Matrix instance
	 */
	public VecmathMatrix3x3F(Matrix3f mat) {
		matrix = mat;
	}
	
	@Override
	public float getFloat(int row, int column) {
		return matrix.getElement(row, column);
	}

	@Override
	public void setFloat(int row, int column, float val) {
		matrix.setElement(row, column, val);
	}

}
