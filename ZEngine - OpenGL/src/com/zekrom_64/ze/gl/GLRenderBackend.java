package com.zekrom_64.ze.gl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLCapabilities;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderOutput;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer.ZEBufferUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZEGraphicsMemory;
import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderThread;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.util.PrimitiveType;
import com.zekrom_64.ze.gl.impl.GLPipeline;
import com.zekrom_64.ze.gl.impl.GLPipelineBuilder;
import com.zekrom_64.ze.gl.objects.GLBuffer;
import com.zekrom_64.ze.gl.objects.GLCommandBuffer;
import com.zekrom_64.ze.gl.objects.GLCommandBufferInterpreted;
import com.zekrom_64.ze.gl.objects.GLIndexBuffer;
import com.zekrom_64.ze.gl.objects.GLRenderEvent;
import com.zekrom_64.ze.gl.objects.GLRenderFence;
import com.zekrom_64.ze.gl.objects.GLRenderSemaphore;
import com.zekrom_64.ze.gl.objects.GLTexture;
import com.zekrom_64.ze.gl.objects.GLVertexBuffer;

public class GLRenderBackend implements ZERenderBackend<GLRenderBackend> {

	private GLRenderOutput output = null;
	private GLNativeContext context = null;
	private GLCapabilities capabilities = null;
	private Set<GLReleasable> releasables = new HashSet<>();
	private GLShaderCompiler shaderCompiler = null;
	private GLPipeline currentPipeline = null;
	private GLVersion glversion;
	
	public GLVersion getVersion() {
		return glversion;
	}
	
	public void setCurrentPipeline(GLPipeline pipeline) {
		if (pipeline != null) {
			if (currentPipeline == null) pipeline.pipelineState.bind();
			else pipeline.pipelineState.bind(currentPipeline.pipelineState);
			currentPipeline = pipeline;
		}
	}
	
	public GLPipeline getCurrentPipeline() {
		return currentPipeline;
	}
	
	public GLRenderOutput getOutput() {
		return output;
	}
	
	public GLNativeContext getNativeContext() {
		return context;
	}
	
	public GLCapabilities getCapabilities() {
		return capabilities;
	}
	
	public void addReleasable(GLReleasable release) {
		releasables.add(release);
	}
	
	@Override
	public boolean supportsFeature(String feature) {
		if (capabilities == null) return false;
		switch(feature) {
		case ZERenderBackend.FEATURE_MULTITHREAD_SYNCHRONIZATION:
			return true;
		case ZERenderBackend.FEATURE_MULTIPLE_PIPELINES:
			return true;
		case ZERenderBackend.FEATURE_MULTIPLE_FRAMEBUFFERS:
			return capabilities.OpenGL30;
		case ZERenderBackend.FEATURE_SHADER_UNIFORM_BUFFER:
			return capabilities.OpenGL31 || capabilities.GL_ARB_uniform_buffer_object;
		case ZERenderBackend.FEATURE_GENERIC_BUFFER:
			return true;
		case ZERenderBackend.FEATURE_COMMAND_DRAW_INDIRECT:
			return capabilities.OpenGL40 ||
					capabilities.GL_ARB_draw_indirect ||
					capabilities.GL_ARB_multi_draw_indirect ||
					capabilities.GL_ARB_base_instance;
		case ZERenderBackend.FEATURE_CUBEMAP_ARRAY:
			return capabilities.OpenGL40 || capabilities.GL_ARB_texture_cube_map_array;
		case ZERenderBackend.FEATURE_TEXTURE_ARRAY:
			return capabilities.OpenGL30 || capabilities.GL_EXT_texture_array;
		}
		return false;
	}

	@Override
	public int getLimitInt(String limit) {
		switch(limit) {
		case ZERenderBackend.LIMIT_INT_MAX_RENDER_WORK_LEVELS: return Integer.MAX_VALUE;
		}
		return 0;
	}

	@Override
	public void init(ZERenderOutput<GLRenderBackend> backend) {
		output = (GLRenderOutput)backend;
		output.context.ensureBound();
		context = GLNativeContext.getNativeContext();
		capabilities = GL.createCapabilities();
		shaderCompiler = new GLShaderCompiler();
		glversion = GLVersion.getFromGL();
	}

	@Override
	public void deinit() {
		context = null;
		capabilities = null;
	}

	@Override
	public ZEPipeline getDefaultPipeline() {
		return null;
	}

	@Override
	public ZEPipelineBuilder createPipelineBuilder() {
		return new GLPipelineBuilder(this);
	}

	@Override
	public void destroyPipeline(ZEPipeline pipeline) { }

	@Override
	public ZEShaderCompiler getShaderCompiler() {
		return shaderCompiler;
	}

	@Override
	public ZEBuffer allocateBuffer(int size, int flags, ZEBufferUsage ... usages) {
		int buf = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buf);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, size, GL15.GL_DYNAMIC_DRAW);
		return new GLBuffer(buf);
	}

	@Override
	public void freeBuffers(ZEBuffer... buffers) {
		int[] bufIDs = new int[buffers.length];
		for(int i = 0; i < buffers.length; i++) bufIDs[i] = ((GLBuffer)buffers[i]).bufferObject;
		releasables.add(() -> GL15.glDeleteBuffers(bufIDs));
	}

	@Override
	public ZEVertexBuffer createVertexBuffer(ZEGraphicsMemory buffer) {
		return new GLVertexBuffer((GLBuffer)buffer);
	}
	
	@Override
	public ZEIndexBuffer createIndexBuffer(ZEGraphicsMemory buffer, PrimitiveType type) {
		return new GLIndexBuffer((GLBuffer)buffer, type);
	}

	@Override
	public ZETexture createTexture(ZETextureDimension dim, int width, int height, int depth, ZEPixelFormat format, ZETextureUsage ... usages) {
		return new GLTexture(width, height, depth, dim, format);
	}

	@Override
	public void destroyTextures(ZETexture... textures) {
		int[] texs = new int[textures.length];
		for(int i = 0; i < texs.length; i++) texs[i] = ((GLTexture)textures[i]).texID;
		releasables.add(() -> GL11.glDeleteTextures(texs));
	}

	@Override
	public ZERenderEvent createRenderEvent() {
		return new GLRenderEvent();
	}

	@Override
	public ZERenderCommandBuffer createCommandBuffer(boolean multipleRecords) {
		return new GLCommandBufferInterpreted(this, multipleRecords);
	}

	@Override
	public ZERenderCommandBuffer[] createCommandBuffers(int count, boolean multipleRecords) {
		GLCommandBuffer[] cmdBufs = new GLCommandBuffer[count];
		for(int i = 0; i < count; i++) cmdBufs[i] = new GLCommandBufferInterpreted(this, multipleRecords);
		return cmdBufs;
	}

	@Override
	public void destroyCommandBuffers(ZERenderCommandBuffer... cmdBufs) { }

	@Override
	public ZERenderThread[] getRenderThreads() {
		return new ZERenderThread[0];
	}

	@Override
	public ZERenderSemaphore createRenderSemaphore() {
		return new GLRenderSemaphore();
	}
	
	public final ZERenderThread primaryRenderThread = new ZERenderThread() {

		@Override
		public synchronized void submitCommands(ZERenderCommandBuffer cmdbuf, ZERenderFence signalFence,
				ZERenderSemaphore[] waitSemaphores, ZERenderSemaphore... signalSemaphores) {
			for(ZERenderSemaphore sem : waitSemaphores) ((GLRenderSemaphore)sem).semaphore.acquireUninterruptibly();
			((GLCommandBuffer)cmdbuf).executeCommands();
			if (signalFence != null) ((GLRenderFence)signalFence).signal();
			for(ZERenderSemaphore sem : signalSemaphores) ((GLRenderSemaphore)sem).semaphore.release();
		}

		@Override
		public void waitIdle() { }

		@Override
		public ZERenderThreadUsage[] getValidUsages() {
			return new ZERenderThreadUsage[] {
				ZERenderThreadUsage.GRAPHICS,
				ZERenderThreadUsage.TRANSFER,
				ZERenderThreadUsage.PRESENT
			};
		}
		
	};

	@Override
	public ZERenderThread getPrimaryRenderThread() {
		return primaryRenderThread;
	}

	@Override
	public void destroyRenderEvents(ZERenderEvent... events) { }

	@Override
	public void destroyRenderSemaphores(ZERenderSemaphore... semaphores) { }

	@Override
	public ZERenderFence createRenderFence() {
		return new GLRenderFence();
	}

	@Override
	public void destroyRenderFences(ZERenderFence... fences) { }

	@Override
	public boolean waitForFences(ZEFenceWaitMode waitMode, long timeout, ZERenderFence... fences) {
		switch(waitMode) {
		case ANY: return GLRenderFence.waitAny(timeout, Arrays.copyOf(fences, fences.length, GLRenderFence[].class));
		case ALL: return GLRenderFence.waitAll(timeout, Arrays.copyOf(fences, fences.length, GLRenderFence[].class));
		}
		return false;
	}

	@Override
	public void resetFences(ZERenderFence... fences) {
		for(ZERenderFence fence : fences) fence.reset();
	}
	
}
