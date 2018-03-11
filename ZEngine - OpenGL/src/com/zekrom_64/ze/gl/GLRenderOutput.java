package com.zekrom_64.ze.gl;

import com.zekrom_64.ze.base.backend.render.ZERenderContext;

public class GLRenderOutput implements ZERenderContext<GLRenderBackend> {

	public final GLContext context;
	
	public GLRenderOutput(GLContext context) {
		this.context = context;
	}

	public void output() {
		if (!context.isBound()) context.bind();
		context.swapBuffers();
	}

	@Override
	public String[] getRequiredFeatures() {
		return new String[0];
	}
	
}
