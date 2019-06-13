package com.zekrom_64.ze.vulkan.objects;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;

public class VKPipeline implements ZEPipeline {

	public final long pipeline;
	
	public VKPipeline(long pipeline) {
		this.pipeline = pipeline;
	}

}
