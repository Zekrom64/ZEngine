package com.zekrom_64.ze.base.backend.render;

/** A shader is a module of code that will be executed by the render backend for a specific purpose.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEShader {
	
	/** Vertex shader type. This will always be available */
	public static final String SHADER_TYPE_VERTEX = "ze.shader.type.vertex";
	/** Tessellation control shader type. This can be passed to {@link ZERenderBackend#supportsFeature(String)} to
	 * test if it is available*/
	public static final String SHADER_TYPE_TESSELLATION_CONTROL = "ze.shader.type.tesselationcontrol";
	/** Tessellation evalutation shader type. This can be passed to {@link ZERenderBackend#supportsFeature(String)} to
	 * test if it is available*/
	public static final String SHADER_TYPE_TESSELLATION_EVALUATION = "ze.shader.type.tessellationevaluation";
	/** Geometry shader type. This can be passed to {@link ZERenderBackend#supportsFeature(String)} to
	 * test if it is available*/
	public static final String SHADER_TYPE_GEOMETRY = "ze.shader.type.geometry";
	/** Fragment shader type. This will always be available */
	public static final String SHADER_TYPE_FRAGMENT = "ze.shader.type.fragment";
	/** Compute shader type. This can be passed to {@link ZERenderBackend#supportsFeature(String)} to
	 * test if it is available*/
	public static final String SHADER_TYPE_COMPUTE = "ze.shader.type.compute";
	
	/** Gets the type of the shader.
	 * 
	 * @return Shader type
	 */
	public String getShaderType();
	
}
