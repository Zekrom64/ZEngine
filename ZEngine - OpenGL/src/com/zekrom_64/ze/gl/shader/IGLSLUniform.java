package com.zekrom_64.ze.gl.shader;

@Deprecated
/** The interface defining a GLSL uniform
 * 
 * @author Zekrom_64
 *
 */
public interface IGLSLUniform {
	
	/** The data types in GLSL.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum UniformType {
		/** A scalar value */
		SCALAR(true),
		/** A vector of data */
		VECTOR(true),
		/** A matrix of data */
		MATRIX(true),
		/** A texture sampler */
		SAMPLER(false),
		/** An image */
		IMAGE(false),
		/** An atomic value */
		ATOMIC(false);
		
		/** The uniform type can have multiple data types (ivec2, bmat4, etc...)*/
		public final boolean isMultiType;
		
		private UniformType(boolean mt) {
			isMultiType = mt;
		}
	}
	
	/** Get the name of this uniform in GLSL shader source code
	 * 
	 * @return Name of the uniform
	 */
	public String getName();
	
}
