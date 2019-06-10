package com.zekrom_64.ze.gl.objects;

import java.util.HashMap;
import java.util.Map;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayoutBuilder;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindType;

public class GLPipelineBindLayoutBuilder implements ZEPipelineBindLayoutBuilder {

	private Map<Integer,GLPipelineBindLayout.GLPipelineBinding> layoutBindings = new HashMap<>();
	
	@Override
	public void addBinding(int binding, ZEPipelineBindType bindType, int arrayCount, String[] shaderStages) {
		GLPipelineBindLayout.GLPipelineBinding pb = new GLPipelineBindLayout.GLPipelineBinding();
		pb.bindType = bindType;
		pb.arrayCount = arrayCount;
		layoutBindings.put(Integer.valueOf(binding), pb);
	}

	@Override
	public ZEPipelineBindLayout build() {
		GLPipelineBindLayout bl = new GLPipelineBindLayout();
		bl.pipelineBindings.putAll(layoutBindings);
		return bl;
	}

}
