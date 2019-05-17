package com.zekrom_64.ze.vulkan.objects;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEVertexInput;

public class VKVertexInput implements ZEVertexInput {
	
	public final int bindingOffset;
	
	public VKVertexInput(int off) {
		bindingOffset = off;
	}

}
