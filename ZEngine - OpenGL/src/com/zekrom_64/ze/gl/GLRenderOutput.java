package com.zekrom_64.ze.gl;

import com.zekrom_64.ze.base.backend.render.ZERenderOutput;

public class GLRenderOutput implements ZERenderOutput<GLRenderBackend> {

	public final GLContext context;
	
	public GLRenderOutput(GLContext context) {
		this.context = context;
	}

	@Override
	public void doOutput(GLRenderBackend backend) {
		context.bind();
		context.swapBuffers();
	}

	@Override
	public String[] getRequiredFeatures() {
		return new String[0];
	}
	
}
