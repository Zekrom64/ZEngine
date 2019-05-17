package com.zekrom_64.ze.vulkan.objects;

import java.util.List;

import com.zekrom_64.ze.base.backend.render.shader.ZEAttributeDescription;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniform;

public class VKShaderProgram implements ZEShaderProgram {

	public final VKShader[] shaders;
	
	public VKShaderProgram(VKShader ... shaders) {
		this.shaders = shaders;
	}

	@Override
	public ZEUniform getUniform(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZEUniform> getUniforms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZEAttributeDescription getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZEAttributeDescription> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
