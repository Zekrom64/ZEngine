package com.zekrom_64.ze.base.backend.render.shader;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;

/** A shader compiler can compile some form of shader source type to a compatible shader object.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEShaderCompiler {

	/** GLSL shader source type. Default for the OpenGL backend.
	 * Any non-null source object is valid, and the {@link Object#toString() toString()} method will be used
	 * to get GLSL source code. This can be passed to {@link ZERenderBackend#supportsFeature(String)} to
	 * test if it is available.
	 */
	public static final String SHADER_SOURCE_TYPE_GLSL = "ze.shader.srctype.glsl";
	
	/** SPIR-V shader source type. Default for the Vulkan backend.
	 * Valid source objects are:
	 * <ul>
	 * <li>int[]</li>
	 * <li>IntBuffer</li>
	 * <li>IntStream</li>
	 * </ul>
	 * This can be passed to {@link ZERenderBackend#supportsFeature(String)} to test if it is available.
	 */
	public static final String SHADER_SOURCE_TYPE_SPIRV = "ze.shader.srctype.spirv";
	
	/** Attempts to compile a shader for the compiler's backend using a source object. Backends may
	 * support multiple source types, specified with the <tt>type</tt> parameter. If <tt>type</tt> is null,
	 * the default for the backend is used.
	 * 
	 * @param src Shader source object
	 * @param type Shader source type
	 * @param shaderType Shader type
	 * @return Compiled shader for backend
	 */
	public ZEShader compileShader(Object src, String type, String shaderType);
	
	/** Deletes a compiled shader.
	 * 
	 * @param shader Shader to delete
	 */
	public void deleteShader(ZEShader shader);
	
	/** Attempts to compile a shader for the compiler's backend using shader modules. Backends may
	 * support different combinations of shader modules, but vertex and fragment shaders are always required.
	 * 
	 * @param modules Modules to compile for program
	 * @return Compiled program for backend
	 */
	public ZEShaderProgram compileShaderProgram(ZEShader ... modules);
	
	/** Deletes a compiled shader program.
	 * 
	 * @param program Program to delete
	 */
	public void deleteShaderProgram(ZEShaderProgram program);
	
}
