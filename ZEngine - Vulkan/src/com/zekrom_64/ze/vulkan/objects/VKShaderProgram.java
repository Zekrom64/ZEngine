package com.zekrom_64.ze.vulkan.objects;

import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;

public class VKShaderProgram implements ZEShaderProgram {

	public final VKShader[] shaders;
	
	public VKShaderProgram(VKShader ... shaders) {
		this.shaders = shaders;
	}
	
}
