package com.zekrom_64.ze.gl.shader;

import java.util.List;

import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;

public class GLShaderProgram implements ZEShaderProgram {
	
	public static class GLAttribute {
		
		public final String name;
		public final int location;
		
		public GLAttribute(String name, int location) {
			this.name = name;
			this.location = location;
		}
		
	}
	
	public static class GLUniform {
		
		public final String name;
		public final int location;
		public final int blockBinding;
		
		public GLUniform(String name, int location, int blockBinding) {
			this.name = name;
			this.location = location;
			this.blockBinding = blockBinding;
		}
		
	}
	
	public final int programObject;
	public final List<GLAttribute> attributes;
	public final List<GLUniform> uniforms;
	
	public GLShaderProgram(int id, List<GLAttribute> attribs, List<GLUniform> uniforms) {
		programObject = id;
		this.attributes = attribs;
		this.uniforms = uniforms;
	}
	
}