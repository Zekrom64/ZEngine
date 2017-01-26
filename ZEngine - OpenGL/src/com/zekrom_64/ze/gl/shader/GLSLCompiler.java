package com.zekrom_64.ze.gl.shader;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;

@Deprecated
/** A customizable GLSL compiler. This wraps OpenGL 2.0's functions and allows custom source preprocessors.
 * 
 * @author Zekrom_64
 */
public class GLSLCompiler {

	/** The type of GLSL shader in the render pipeline.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum GLSLShaderType {
		VERTEX(GL20.GL_VERTEX_SHADER),
		FRAGMENT(GL20.GL_FRAGMENT_SHADER),
		TESSELATION(GL40.GL_TESS_CONTROL_SHADER),
		GEOMETRY(GL32.GL_GEOMETRY_SHADER);
		
		/** The OpenGL enumeration value */
		public final int glenum;
		private GLSLShaderType(int e) {
			glenum = e;
		}
	}
	
	/** The list of preprocessors used in compilation */
	public final ArrayList<GLSLPreprocessor> preprocessors = new ArrayList<>();
	
	/** The type of shader targeted by the compiler */
	public final GLSLShaderType type;
	
	public GLSLCompiler(GLSLShaderType t) {
		type = t;
	}
	
	/** Compiles shader source code to a program in the render pipeline. This first puts
	 * the source code through the registered preprocessors, compiles the source to
	 * the new shader, and then tests if the shader successfully compiled.
	 * 
	 * @param source Shader source code
	 * @return The completed shader
	 * @throws GLSLCompilerException If there is a compilation error
	 */
	public int compile(String source) throws GLSLCompilerException {
		int shader = GL20.glCreateShader(type.glenum);
		GL20.glShaderSource(shader, preprocess(source));
		GL20.glCompileShader(shader);
		int error = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		if (error==GL11.GL_FALSE) {
			String log = GL20.glGetShaderInfoLog(shader);
			throw new GLSLCompilerException(log, shader);
		}
		return shader;
	}
	
	private String preprocess(String input) throws GLSLCompilerException {
		return input;
	}
	
}
