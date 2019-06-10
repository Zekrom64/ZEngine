package com.zekrom_64.ze.gl.objects;

import java.util.HashMap;
import java.util.Map;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindPool;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.gl.GLRenderBackend;

public class GLPipelineBindPool implements ZEPipelineBindPool {

	public final GLRenderBackend backend;
	
	public GLPipelineBindPool(GLRenderBackend backend) {
		this.backend = backend;
	}
	
	@Override
	public void free(ZEPipelineBindSet bindSet) { }

	@Override
	public ZEPipelineBindSet allocate(ZEPipelineBindLayout... layouts) {
		Map<Integer,GLPipelineBindLayout.GLPipelineBinding> bindings = new HashMap<>();
		for(ZEPipelineBindLayout bl : layouts) bindings.putAll(((GLPipelineBindLayout)bl).pipelineBindings);
		return new GLPipelineBindSet(backend, bindings);
	}

}
