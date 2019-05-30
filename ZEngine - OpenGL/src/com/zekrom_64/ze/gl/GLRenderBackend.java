package com.zekrom_64.ze.gl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GLCapabilities;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderOutput;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer.ZEBufferUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebufferBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderThread;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.gl.objects.GLBuffer;
import com.zekrom_64.ze.gl.objects.GLCommandBuffer;
import com.zekrom_64.ze.gl.objects.GLCommandBufferInterpreted;
import com.zekrom_64.ze.gl.objects.GLFramebufferBuilder;
import com.zekrom_64.ze.gl.objects.GLPipeline;
import com.zekrom_64.ze.gl.objects.GLPipelineBuilder;
import com.zekrom_64.ze.gl.objects.GLRenderEvent;
import com.zekrom_64.ze.gl.objects.GLRenderFence;
import com.zekrom_64.ze.gl.objects.GLRenderPassBuilder;
import com.zekrom_64.ze.gl.objects.GLRenderSemaphore;
import com.zekrom_64.ze.gl.objects.GLTexture;
import com.zekrom_64.ze.gl.shader.GLShaderCompiler;

public class GLRenderBackend implements ZERenderBackend<GLRenderBackend> {

	private GLRenderOutput output = null;
	private GLNativeContext context = null;
	private GLCapabilities capabilities = null;
	private Set<GLReleasable> releasables = new HashSet<>();
	private GLShaderCompiler shaderCompiler = null;
	private GLPipeline currentPipeline = null;
	private GLVersion glversion;
	private GLExtensions extensions;
	
	/** Enumeration of error checking modes for the OpenGL backend. Because
	 * OpenGL requires polling for errors, the backend allows for controlling
	 * when this polling is done. Polling will at least always be done
	 * before presentation by the render output.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum GLErrorChecking {
		/** No error checking is performed. */
		OFF,
		/** Error checking is performed at the end of every group of
		 * operations made for a particular method call.
		 */
		COARSE,
		/** Error checking is performed after every OpenGL call. */
		FINE
	}
	
	private GLErrorChecking errorChecking = GLErrorChecking.OFF;
	
	/** Sets the error checking mode.
	 * 
	 * @param checking Error checking mode
	 */
	public void setErrorChecking(GLErrorChecking checking) {
		if (checking != null) errorChecking = checking;
	}
	
	/** Gets the error checking mode.
	 * 
	 * @return Error checking mode
	 */
	public GLErrorChecking getErrorChecking() {
		return errorChecking;
	}
	
	/** Checks for an OpenGL error if error checking is at a fine level.
	 * 
	 */
	public void checkErrorFine() {
		checkErrorFine(null);
	}
	
	/** Checks for an OpenGL error if error checking is at a fine level.
	 * 
	 * @param msg Message to pass to error if thrown
	 */
	public void checkErrorFine(String msg) {
		if (errorChecking == GLErrorChecking.FINE) {
			int err = GL11.glGetError();
			if (err != GL11.GL_NO_ERROR) {
				if (msg != null) throw new GLException(err);
				else throw new GLException(msg, err);
			}
		}
	}
	
	/** Checks for an OpenGL error if error checking is at a coarse level.
	 * 
	 */
	public void checkErrorCoarse() {
		checkErrorCoarse(null);
	}
	
	/** Checks for an OpenGL error if error checking is at a coarse level.
	 * 
	 * @param msg Message to pass to error if thrown
	 */
	public void checkErrorCoarse(String msg) {
		if (errorChecking == GLErrorChecking.FINE) {
			int err = GL11.glGetError();
			if (err != GL11.GL_NO_ERROR) {
				if (msg != null) throw new GLException(err);
				else throw new GLException(msg, err);
			}
		}
	}
	
	
	
	// ------------------
	// | OpenGL Methods |
	// ------------------
	
	/** Gets the OpenGL version of the current backend.
	 * 
	 * @return OpenGL version
	 */
	public GLVersion getVersion() {
		return glversion;
	}
	
	/** Sets the current pipeline of the backend's OpenGL context.
	 * 
	 * @param pipeline Pipeline to set
	 */
	public void setCurrentPipeline(GLPipeline pipeline) {
		context.ensureBound();
		if (pipeline != null) {
			if (currentPipeline == null) pipeline.pipelineState.bind();
			else pipeline.pipelineState.bind(currentPipeline.pipelineState);
			currentPipeline = pipeline;
		}
	}
	
	/** Gets the current pipeline bound to the backend.
	 * 
	 * @return Currently bound pipeline
	 */
	public GLPipeline getCurrentPipeline() {
		return currentPipeline;
	}
	
	/** Gets the render output bound to the backend.
	 * 
	 * @return Backend's render output
	 */
	public GLRenderOutput getOutput() {
		return output;
	}
	
	/** Gets the native OpenGL context.
	 * 
	 * @return OpenGL context
	 */
	public GLNativeContext getNativeContext() {
		return context;
	}
	
	/** Gets the current capabilities of the backend.
	 * 
	 * @return OpenGL capabilities
	 */
	public GLCapabilities getCapabilities() {
		return capabilities;
	}
	
	/** Adds a releasable object to be released at some point
	 * with an OpenGL context.
	 * 
	 * @param release Releasable object
	 */
	public void addReleasable(GLReleasable release) {
		synchronized(releasables) {
			releasables.add(release);
		}
	}
	
	/** Gets the extensions for the current OpenGL context.
	 * 
	 * @return OpenGL extensions
	 */
	public GLExtensions getExtensions() {
		return extensions;
	}
	
	// ----------------
	// | Limit Values |
	// ----------------
	// All limits are read when the context is initialized to avoid stealing
	//    the context every time a limit is needed
	
	private float limitFloatMaxAnisotropy;
	
	@Override
	public boolean supportsFeature(String feature) {
		if (capabilities == null) return false;
		switch(feature) {
		case ZERenderBackend.FEATURE_MULTITHREAD_SYNCHRONIZATION:
			return true;
		case ZERenderBackend.FEATURE_MULTIPLE_PIPELINES:
			return true;
		case ZERenderBackend.FEATURE_MULTIPLE_FRAMEBUFFERS:
			return extensions.framebufferObjects;
			
		case ZERenderBackend.FEATURE_MULTILEVEL_RENDER_WORK_BUFFER:
			return true;
			
		case ZERenderBackend.FEATURE_DEPTH_BIAS_CLAMP:
			return extensions.polygonOffsetClamp;
		case ZERenderBackend.FEATURE_PER_INSTANCE_VERTEX_BINDING:
			return extensions.vertexBindingDivisor;
			
		case ZERenderBackend.FEATURE_SHADER_UNIFORM_BUFFER:
			return capabilities.OpenGL31 || capabilities.GL_ARB_uniform_buffer_object;
		case ZERenderBackend.FEATURE_SHADER_STORAGE_BUFFER:
			return capabilities.OpenGL43 || capabilities.GL_ARB_shader_storage_buffer_object;
		case ZERenderBackend.FEATURE_SHADER_STORAGE_IMAGE:
			return capabilities.OpenGL42 ||
					capabilities.GL_ARB_shader_image_load_store ||
					capabilities.GL_EXT_shader_image_load_store;
			
		case ZERenderBackend.FEATURE_COMMAND_DRAW_INDIRECT:
			return capabilities.OpenGL40 ||
					capabilities.GL_ARB_draw_indirect ||
					capabilities.GL_ARB_multi_draw_indirect ||
					capabilities.GL_ARB_base_instance;
			
		case ZERenderBackend.FEATURE_CUBEMAP_ARRAY:
			return capabilities.OpenGL40 || capabilities.GL_ARB_texture_cube_map_array;
		case ZERenderBackend.FEATURE_TEXTURE_ARRAY:
			return capabilities.OpenGL30 || capabilities.GL_EXT_texture_array;
		case ZERenderBackend.FEATURE_INDEPENDENT_SAMPLER:
			return capabilities.OpenGL33 || capabilities.GL_ARB_sampler_objects;
		case ZERenderBackend.FEATURE_ANISOTROPY:
			return capabilities.GL_EXT_texture_filter_anisotropic;
		}
		return false;
	}
	
	private int limitIntMaxVertexBindings;

	@Override
	public int getLimitInt(String limit) {
		switch(limit) { // OpenGL doesn't have a lot of hard limits if something is done in software
		case ZERenderBackend.LIMIT_INT_MAX_RENDER_WORK_LEVELS: return Integer.MAX_VALUE;
		case ZERenderBackend.LIMIT_INT_MAX_INLINE_UPLOAD_SIZE: return Integer.MAX_VALUE;
		case ZERenderBackend.LIMIT_INT_MAX_VERTEX_BINDINGS: return limitIntMaxVertexBindings;
		}
		return 0;
	}
	
	@Override
	public float getLimitFloat(String limit) {
		switch(limit) {
		case ZERenderBackend.LIMIT_FLOAT_MAX_ANISOTROPY_LEVEL: return limitFloatMaxAnisotropy;
		}
		return 0;
	}

	@Override
	public void init(ZERenderOutput<GLRenderBackend> backend) {
		output = (GLRenderOutput)backend;
		output.context.ensureBound();
		context = GLNativeContext.getCurrentContext();
		capabilities = GL.createCapabilities();
		shaderCompiler = new GLShaderCompiler(this);
		glversion = GLVersion.getFromGL();
		extensions = new GLExtensions(capabilities);
		
		context.executeExclusivly(() -> {
			// Get limit values
			if (capabilities.GL_EXT_texture_filter_anisotropic)
				limitFloatMaxAnisotropy = GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
			
			if (capabilities.OpenGL43) limitIntMaxVertexBindings = GL11.glGetInteger(GL43.GL_MAX_VERTEX_ATTRIB_BINDINGS);
			else limitIntMaxVertexBindings = 1;
		});
	}

	@Override
	public void deinit() {
		output = null;
		context = null;
		capabilities = null;
	}

	@Override
	public ZEFramebufferBuilder createFramebufferBuilder() {
		return new GLFramebufferBuilder(this);
	}

	@Override
	public ZERenderPassBuilder createRenderPassBuilder() {
		return new GLRenderPassBuilder();
	}

	@Override
	public ZEPipelineBuilder createPipelineBuilder() {
		return new GLPipelineBuilder(this);
	}

	@Override
	public void destroyPipeline(ZEPipeline pipeline) { } // Pipeline is just a collection of settings, doesn't need to be destroyed

	@Override
	public ZEShaderCompiler getShaderCompiler() {
		return shaderCompiler;
	}

	@Override
	public ZEBuffer allocateBuffer(long size, ZEBufferUsage[] usages, ZEBufferParameter ... params) {
		context.bindExclusively();
		int buf = GL15.glGenBuffers();
		checkErrorFine();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buf);
		checkErrorFine();
		if (capabilities.glBufferStorage != 0) {
			GL44.glBufferStorage(GL15.GL_ARRAY_BUFFER, size, GL30.GL_MAP_READ_BIT | GL30.GL_MAP_WRITE_BIT | GL44.GL_DYNAMIC_STORAGE_BIT);
		} else {
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, size, GL15.GL_DYNAMIC_DRAW);
		}
		checkErrorFine();
		checkErrorCoarse("Failed to allocate buffer");
		context.unbindExclusively();
		return new GLBuffer(this, buf, size);
	}

	@Override
	public void freeBuffers(ZEBuffer... buffers) {
		int[] bufIDs = new int[buffers.length];
		for(int i = 0; i < buffers.length; i++) bufIDs[i] = ((GLBuffer)buffers[i]).bufferObject;
		releasables.add(() -> GL15.glDeleteBuffers(bufIDs));
	}

	@Override
	public ZETexture createTexture(ZETextureDimension dim, int width, int height, int depth,
			ZEPixelFormat format, ZETextureUsage[] usages, ZETextureParameter ... params) {
		int mipLevels = 1;
		int arrayLevels = 0;
		for(ZETextureParameter param : params) {
			if (param instanceof ZETextureParamMipmap) {
				mipLevels = ((ZETextureParamMipmap)param).mipmapLevels;
			} else if (param instanceof ZETextureParamArray) {
				arrayLevels = ((ZETextureParamArray)param).arrayLayers;
			}
		}
		context.bindExclusively();
		int gltex = GL11.glGenTextures();
		checkErrorFine();
		GLTexture tex = new GLTexture(this, gltex, dim, width, height, depth, arrayLevels, mipLevels, format);
		context.unbindExclusively();
		return tex;
	}

	@Override
	public void destroyTextures(ZETexture... textures) {
		int[] texs = new int[textures.length];
		for(int i = 0; i < texs.length; i++) texs[i] = ((GLTexture)textures[i]).textureObject;
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
	
	/** The primary render thread for the OpenGL backend */
	public final ZERenderThread primaryRenderThread = new ZERenderThread() {

		@Override
		public synchronized void submitCommands(ZERenderCommandBuffer cmdbuf, ZERenderFence signalFence,
				ZERenderSemaphore[] waitSemaphores, ZERenderSemaphore... signalSemaphores) {
			context.executeExclusivly(() -> {
				for(ZERenderSemaphore sem : waitSemaphores) ((GLRenderSemaphore)sem).semaphore.acquireUninterruptibly();
				((GLCommandBuffer)cmdbuf).executeCommands();
				if (signalFence != null) ((GLRenderFence)signalFence).signal();
				for(ZERenderSemaphore sem : signalSemaphores) ((GLRenderSemaphore)sem).semaphore.release();
				synchronized(releasables) {
					for(GLReleasable r : releasables) r.releaseWithGL();
					releasables.clear();
				}
			});
			
		}

		@Override
		public void waitIdle() { } // Not really necessary

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
