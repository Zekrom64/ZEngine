package com.zekrom_64.ze.base.backend.render.shader;

/** <p>Interface for shader uniform variables. Abstracts setting and getting uniform values for different
 * render backends.</p>
 * 
 * @author Zekrom_64
 *
 */
public interface ZEUniform {

	/** Gets the name of the uniform in the shader program.
	 * 
	 * @return Uniform name
	 */
	public String getName();
	
	/** Gets the number of elements in the uniform. If this is 1, this is either a single value, or an array of
	 * length 1. Otherwise, this is the length of the array in elements. This should never return a value
	 * less than 0.
	 * 
	 * @return Array size of uniform
	 */
	public default int getLength() {
		return 1;
	}
	
	/** Gets the shader type of the uniform.
	 * 
	 * @return Shader uniform type
	 */
	public ZEShaderType getShaderType();
	
}
