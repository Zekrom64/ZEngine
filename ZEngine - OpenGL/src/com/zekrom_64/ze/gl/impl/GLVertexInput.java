package com.zekrom_64.ze.gl.impl;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEVertexInput;

public class GLVertexInput implements ZEVertexInput {
	
	public final int bindingIndex;
	
	public GLVertexInput(int bindingIndex) {
		this.bindingIndex = bindingIndex;
	}
	
}
