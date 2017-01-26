package com.zekrom_64.ze.gl.shader;

/** The basic data types in GLSL. For GLSL versions below 1.30, the documented sizes
 * may not be applicable.
 * 
 * @author Zekrom_64
 *
 */
public enum GLSLType {
	/** A boolean data type, this is really just a 1 or 0 integer */
	BOOL,
	/** A 32-bit signed integer */
	INT,
	/** A 32-bit unsigned integer */
	UINT,
	/** A 32-bit IEEE-754 single precision floating point number */
	FLOAT,
	/** A 64-bit IEEE-754 double precision floating point number. This is only available with
	 * OpenGL 4.0 or ARB_gpu_shader_fp64
	 */
	DOUBLE;
	
}
