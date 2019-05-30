package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.gl.GLRenderBackend;

public class GLPipeline implements ZEPipeline {
	
	public final GLRenderBackend backend;
	public final GLPipelineState pipelineState;
	
	public GLPipeline(GLRenderBackend backend) {
		this.backend = backend;
		pipelineState = new GLPipelineState(backend);
	}
	
	public GLPipeline clone() {
		GLPipeline newpipeline = new GLPipeline(backend);
		newpipeline.pipelineState.set(pipelineState);
		return newpipeline;
	}

}
