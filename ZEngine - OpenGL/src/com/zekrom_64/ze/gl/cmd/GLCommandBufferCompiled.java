package com.zekrom_64.ze.gl.cmd;

import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderWorkRecorder;
import com.zekrom_64.ze.gl.GLRenderBackend;

public class GLCommandBufferCompiled extends GLCommandBuffer {

	private Runnable compiledFunction = null;
	private GLCommandBufferCompiler compiler = null;
	
	public GLCommandBufferCompiled(GLRenderBackend backend, boolean rewritable) {
		super(backend, rewritable);
	}

	@Override
	public ZERenderWorkRecorder beginRecording() {
		if (compiledFunction != null && !isRewritable) return null;
		if (compiler == null) compiler = new GLCommandBufferCompiler(backend);
		return compiler;
	}

	@Override
	public ZERenderWorkRecorder beginRecording(ZERenderCommandBuffer parent) {
		return beginRecording();
	}

	@Override
	public void endRecording() {
		GLCommandBufferCompiler comp = compiler;
		if (comp != null) {
			compiledFunction = comp.compile();
			compiler = null;
		}
	}

	@Override
	public void executeCommands() {
		Runnable func = compiledFunction;
		if (func != null) func.run();
	}

}
