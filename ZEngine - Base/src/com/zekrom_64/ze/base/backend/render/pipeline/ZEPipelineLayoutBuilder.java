package com.zekrom_64.ze.base.backend.render.pipeline;

public interface ZEPipelineLayoutBuilder {

	public void setBindLayouts(ZEPipelineBindLayout ... layouts);
	
	public ZEPipelineLayout build();
	
}
