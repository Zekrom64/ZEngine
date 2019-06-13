package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineLayoutBuilder;

public class GLPipelineLayoutBuilder implements ZEPipelineLayoutBuilder {

	@Override
	public void setBindLayouts(ZEPipelineBindLayout... layouts) { }

	@Override
	public ZEPipelineLayout build() {
		return new GLPipelineLayout();
	}

}
