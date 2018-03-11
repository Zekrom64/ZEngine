package com.zekrom_64.ze.base.backend.render.shader;

import java.util.List;

/** <p>A shader program uses multiple shader modules to form the shader segment of a pipeline.</p>
 * 
 * <p>Notes:<br/>
 * <ul>
 * <li><b>OpenGL 2.0</b> - Uniform setting can be accelerated if <code>ARB_separate_shader_objects</code> or OpenGL >= 4.1 are available</li>
 * </ul>
 * </p>
 * 
 * @author Zekrom_64
 *
 */
public interface ZEShaderProgram {
	
	/** Attempts to get a uniform variable from the shader program. If no uniform with the given name is found,
	 * null is returned.
	 * 
	 * @param name Name of uniform
	 * @return Uniform variable, or null
	 */
	public ZEUniform getUniform(String name);
	
	/** Gets all the known uniform variables in the shader program. Each call will check the program object for
	 * all known uniforms.
	 * 
	 * @return List of known uniforms
	 */
	public List<ZEUniform> getUniforms();
	
	public ZEAttributeDescription getAttribute(String name);
	
	public List<ZEAttributeDescription> getAttributes();
	
}
