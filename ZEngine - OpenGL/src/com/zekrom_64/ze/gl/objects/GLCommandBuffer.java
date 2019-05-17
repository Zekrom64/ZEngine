package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.gl.GLRenderBackend;

public abstract class GLCommandBuffer implements ZERenderCommandBuffer {

	public final GLRenderBackend backend;
	public final boolean isRewritable;
	
	public GLCommandBuffer(GLRenderBackend backend, boolean rewritable) {
		this.backend = backend;
		isRewritable = rewritable;
	}
	
	public abstract void executeCommands();
	
}
