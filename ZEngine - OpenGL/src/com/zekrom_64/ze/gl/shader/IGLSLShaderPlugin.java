package com.zekrom_64.ze.gl.shader;

@Deprecated
/** Interface for plugins that can be used instead of include files to provide information 
 * 
 * @author Zekrom_64
 *
 */
public interface IGLSLShaderPlugin {

	/** Gets the text to include in the shader source.
	 * 
	 * @return Shader source code
	 */
	public String getShaderText();

	/** Get the uniforms used by this plugin.
	 * 
	 * @return Plugin uniforms
	 */
	public IGLSLUniform[] getUniforms();
	
}
