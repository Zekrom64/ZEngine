package com.zekrom_64.ze.gl.shader;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import com.zekrom_64.ze.base.backend.render.shader.ZEShader;

public class GLShader implements ZEShader {

	public final int shaderObject;
	public final int gltype;
	
	public GLShader(int id, int gltype) {
		shaderObject = id;
		this.gltype = gltype;
	}
	
	@Override
	public String getShaderType() {
		switch(gltype) {
		case GL20.GL_VERTEX_SHADER: return ZEShader.SHADER_TYPE_VERTEX;
		case GL20.GL_FRAGMENT_SHADER: return ZEShader.SHADER_TYPE_FRAGMENT;
		case GL32.GL_GEOMETRY_SHADER: return ZEShader.SHADER_TYPE_GEOMETRY;
		case GL40.GL_TESS_CONTROL_SHADER: return ZEShader.SHADER_TYPE_TESSELLATION_CONTROL;
		case GL40.GL_TESS_EVALUATION_SHADER: return ZEShader.SHADER_TYPE_TESSELLATION_EVALUATION;
		case GL43.GL_COMPUTE_SHADER: return ZEShader.SHADER_TYPE_COMPUTE;
		}
		return null;
	}

}