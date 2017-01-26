package com.zekrom_64.ze.gl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;

import com.zekrom_64.ze.base.backend.render.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.ZERenderOutput;
import com.zekrom_64.ze.base.backend.render.ZEShaderCompiler;
import com.zekrom_64.ze.base.err.ZEngineInternalException;

public class GL20RenderBackend implements GLRenderBackend {

	private GLRenderOutput output = null;
	private GLCapabilities capabilities = null;
	private boolean running = false;
	private final List<GLRenderWork> renderWork = new ArrayList<>();
	private final Thread renderThread = new Thread() {
		
		@Override
		public void run() {
			output.context.bind();
			capabilities = GL.createCapabilities();
			while(running) {
				GL11.glClearColor(0, 0, 0, 1);
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glPushMatrix();
				for(GLRenderWork work : renderWork) work.doWork();
				GL11.glPopMatrix();
				output.doOutput(GL20RenderBackend.this);
			}
		}
		
	};
	
	public class GL20Pipeline implements ZEPipeline {

		private GL20Pipeline() {}
		
		@Override
		public ZEShaderCompiler<?> getShaderCompiler() {
			return GLShaderCompiler.INSTANCE;
		}
		
	}
	
	public final GL20Pipeline pipeline = new GL20Pipeline();
	
	@Override
	public boolean supportsFeature(String feature) {
		return false;
	}

	@Override
	public void init(ZERenderOutput<GLRenderBackend> backend) {
		if (backend instanceof GLRenderOutput) {
			output = (GLRenderOutput)backend;
			running = true;
			renderThread.start();
		} else ZEngineInternalException.throwInternallyNoExcept("An OpenGL render backend must be initialized with a GLRenderOutput");
	}

	@Override
	public void deinit() {
		running = false;
		while(renderThread.isAlive()) {
			try {
				renderThread.join();
			} catch (InterruptedException e) {}
		}
	}

	@Override
	public ZEPipeline createPipeline() {
		return pipeline;
	}
	
	public static interface GLRenderWork extends ZERenderWork {
		
		public void doWork();
		
	}

	@Override
	public void scheduleOnce(ZERenderWork[] work, Consumer<Void> finished) {
		finished.accept(null);
	}

	@Override
	public void reschedule(ZERenderWork... work) {
		
	}

	@Override
	public ZERenderWorkFactory getWorkFactory() {
		return null;
	}

	@Override
	public ZEPipeline getDefaultPipeline() {
		return pipeline;
	}

	@Override
	public ZEPipelineBuilder createPipelineBuilder() {
		return null;
	}

}
