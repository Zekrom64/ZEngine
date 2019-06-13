package com.zekrom_64.mathlib.compat.vecmath;

import javax.vecmath.Matrix4f;

import com.zekrom_64.mathlib.matrix.Matrix4x4;
import com.zekrom_64.mathlib.matrix.MatrixFloat;

/** Compatibility class wrapping a Vecmath {@link javax.vecmath.Matrix4f Matrix4f}.
 * 
 * @author Zekrom_64
 *
 */
public class VecmathMatrix4x4F implements Matrix4x4<Float>, MatrixFloat {

	/** The matrix reference. */
	public final Matrix4f matrix;
	
	/** Creates a matrix wrapper with a new matrix instance. */
	public VecmathMatrix4x4F() {
		matrix = new Matrix4f();
	}
	
	/** Creates a matrix wrapper with a given matrix instance.
	 * 
	 * @param mat Matrix instance
	 */
	public VecmathMatrix4x4F(Matrix4f mat) {
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
