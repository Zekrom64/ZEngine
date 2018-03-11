package com.zekrom_64.ze.vulkan.objects;

import com.zekrom_64.ze.base.backend.render.shader.ZEShader;

public class VKShader implements ZEShader {

	public final long shaderModule;
	public final String shaderType;
	
	public VKShader(long module, String type) {
		shaderModule = module;
		shaderType = type;
	}
	
	@Override
	public String getShaderType() {
		return shaderType;
	}

}
