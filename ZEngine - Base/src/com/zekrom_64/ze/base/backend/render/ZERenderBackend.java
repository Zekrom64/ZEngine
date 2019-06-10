package com.zekrom_64.ze.base.backend.render;

import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer.ZEBufferUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebufferBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderThread;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayoutBuilder;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindPool;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.image.ZEPixelFormat;

/** A render backend 
 * 
 * @author Zekrom_64
 *
 * @param <B>
 */
public interface ZERenderBackend<B extends ZERenderBackend<?>> {
	
	// -------------------
	// | FEATURE STRINGS |
	// -------------------
	
	// Multithreading / Multi-object
	
	/** The render backend supports multithreaded render work. */
	public static final String FEATURE_MULTITHREAD_RENDERING = "ze.feature.multithread.render";
	/** The render backend supports synchronization tools. */
	public static final String FEATURE_MULTITHREAD_SYNCHRONIZATION = "ze.features.multithread.synchronization";
	/** The render backend supports multiple pipeline objects. */
	public static final String FEATURE_MULTIPLE_PIPELINES = "ze.feature.multi.pipeline";
	/** The render backend supports multiple framebuffer objects. */
	public static final String FEATURE_MULTIPLE_FRAMEBUFFERS = "ze.feature.multi.framebuffer";
	
	// Render work features
	
	/** The render backend supports multilevel render work buffer recording. */
	public static final String FEATURE_MULTILEVEL_RENDER_WORK_BUFFER = "ze.features.renderWork.multilevelBuffer";
	
	// Pipeline features
	
	/** The render backend supports pipeline caching. */
	public static final String FEATURE_CACHEABLE_PIPELINE = "ze.feature.cache.pipeline";
	/** The render backend supports the <tt>clamp</tt> parameter in {@link ZERenderWorkRecorder#setDepthBias(double, double, double) setDepthBias}.*/
	public static final String FEATURE_DEPTH_BIAS_CLAMP = "ze.feature.depthBias.clamp";
	/** The render backend supports using per-instance vertex attributes supplied from a vertex binding. */
	public static final String FEATURE_PER_INSTANCE_VERTEX_BINDING = "ze.features.vertexBinding.perInstance";
	
	// Shader features
	
	/** The render backend supports shader caching. */
	public static final String FEATURE_CAHCEABLE_SHADER = "ze.feature.cache.shader";
	/** The render backend supports modular shader usage (i.e. a single shader object can be used for
	 * multiple shader stages). */
	public static final String FEATURE_SHADER_MODULAR = "ze.features.shader.modular";
	/** The render backend supports compute shaders that can operate closely with graphics work. */
	public static final String FEATURE_SHADER_COMPUTE_AVAILALE = "ze.features.shader.computeavailable";
	/** The render backend supports uniform buffer objects. */
	public static final String FEATURE_SHADER_UNIFORM_BUFFER = "ze.features.shader.ubo";
	/** The render backend supports uniform buffer objects. */
	public static final String FEATURE_SHADER_STORAGE_BUFFER = "ze.features.shader.sbo";
	/** The render backend supports storage images. */
	public static final String FEATURE_SHADER_STORAGE_IMAGE = "ze.features.shader.storageImage";
	/** The render backend supports input attachments. */
	public static final String FEATURE_SHADER_INPUT_ATTACHMENT = "ze.features.shader.inputAttachment";
	
	// Bind set features
	
	/** The render backend supports primitive writes to a bind set. */
	public static final String FEATURE_BIND_SET_PRIMTIVE_WRITE = "ze.features.bindSet.primitiveWrite";
	/** The render backend supports writing from buffer objects to a bind set. */
	public static final String FEATURE_BIND_SET_BUFFER_WRITE = "ze.features.bindSet.bufferWrite";
	
	// Object features
	
	/** The render backend supports object paramters defining the memory to be device local only. */
	public static final String FEATURE_DEVICE_LOCAL_MEMORY = "ze.features.memory.deviceLocal";
	
	// Drawing features

	/** The render backend supports commands for indirect drawing. */
	public static final String FEATURE_COMMAND_DRAW_INDIRECT = "ze.features.command.indirectDraw";
	
	// Texture features
	
	/** The render backend supports array texture cubemaps */
	public static final String FEATURE_CUBEMAP_ARRAY = "ze.features.texture.cubemap";
	/** The render backend supports array textures. */
	public static final String FEATURE_TEXTURE_ARRAY = "ze.features.texture.array";
	/** The render backend supports sampler objects independent of the texture. */
	public static final String FEATURE_INDEPENDENT_SAMPLER = "ze.features.texture.sampler";
	/** The render backend supports anisotropy for texture sampling. */
	public static final String FEATURE_ANISOTROPY = "ze.features.texture.anisotropy";

	// -------------------
	// | FEATURE SUPPORT |
	// -------------------
	
	/** Tests if the render backend supports a given feature. If the given feature string is not
	 * recognized, false will be returned.
	 * 
	 * @param feature The feature to test for
	 * @return If the feature is available
	 */
	public boolean supportsFeature(String feature);
	
	// ----------
	// | LIMITS |
	// ----------
	
	/** The maximum number of sub-levels supported by the render backend. */
	public static final String LIMIT_INT_MAX_RENDER_WORK_LEVELS = "ze.limit.renderWork.levels";
	/** The maximum number of bytes that can be uploaded by a {@link ZERenderWorkRecorder#uploadToBuffer} call. */
	public static final String LIMIT_INT_MAX_INLINE_UPLOAD_SIZE = "ze.limit.renderWork.uploadSize";
	/** The maximum number of vertex bindings that can be used with pipelines. */
	public static final String LIMIT_INT_MAX_VERTEX_BINDINGS = "ze.limit.pipeline.vertexBindings";
	
	/** Gets the integer value of a limit for the backend.
	 * 
	 * @param limit The integer limit to get
	 * @return The integer limit value
	 */
	public int getLimitInt(String limit);
	
	/** The maximum level of anisotropy available. */
	public static final String LIMIT_FLOAT_MAX_ANISOTROPY_LEVEL = "ze.limit.texture.anisotropy";
	
	/** Gets the float value of a limit for the backend.
	 * 
	 * @param limit The float limit to get
	 * @return The float limit value
	 */
	public float getLimitFloat(String limit);
	
	// -------------------
	// | INIT AND DEINIT |
	// -------------------
	
	/** Initializes the render backend and presents to the given render output.
	 * 
	 * @param output Output to render to
	 */
	public void init(ZERenderOutput<B> output);
	
	/** Deinitializes the render backend, stopping presentation to the
	 * current render output.
	 */
	public void deinit();
	
	// -------------
	// | PIPELINES |
	// -------------
	
	/** Creates a new pipeline builder if the backend supports multiple pipeline objects. If the backend does
	 * not support multiple pipeline objects, null is returned.
	 * 
	 * @return Pipeline builder
	 */
	public ZEPipelineBuilder createPipelineBuilder();
	
	/** Destroys a pipeline.
	 * 
	 * @param pipeline Pipeline
	 */
	public void destroyPipeline(ZEPipeline pipeline);
	
	public ZEPipelineBindLayoutBuilder createPipelineBindLayoutBuilder();
	
	public void destroyPipelineBindLayout(ZEPipelineBindLayout pipelineBindLayout);
	
	public static class ZEBindingCount {
		
		public ZEPipelineBindType bindingType;
		public int bindingCount;
		
		public ZEBindingCount(ZEPipelineBindType bindType, int bindCount) {
			bindingType = bindType;
			bindingCount = bindCount;
		}
		
	}
	
	public ZEPipelineBindPool createBindPool(ZEBindingCount[] allocBindings);
	
	public void destroyBindPool(ZEPipelineBindPool bindPool);
	
	// -----------
	// | SHADERS |
	// -----------
	
	/** Gets the compiler for compiling compatible shaders.
	 * 
	 * @return Shader compiler
	 */
	public ZEShaderCompiler getShaderCompiler();
	
	// ----------------
	// | FRAMEBUFFERS |
	// ----------------
	
	/** Creates a new framebuffer builder.
	 * 
	 * @return Framebuffer builder
	 */
	public ZEFramebufferBuilder createFramebufferBuilder();
	
	// -----------------
	// | RENDER PASSES |
	// -----------------
	
	/** Creates a new render pass builder.
	 * 
	 * @return Render pass builder
	 */
	public ZERenderPassBuilder createRenderPassBuilder();
	
	// ------------------
	// | MEMORY BUFFERS |
	// ------------------
	
	/** A buffer parameter is an extra parameter that can be passed during a
	 * buffer's creation to enable certain features.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZEBufferParameter { }
	
	/** Creates a buffer of the given size.
	 * 
	 * @param size Buffer size
	 * @param flags Buffer flags
	 * @param usages Valid buffer usages
	 * @return Memory buffer
	 */
	public ZEBuffer allocateBuffer(long size, ZEBufferUsage[] usages, ZEBufferParameter ... params);
	
	/** Frees a set of memory buffers
	 * 
	 * @param buffers Memory buffers
	 */
	public void freeBuffers(ZEBuffer ... buffers);
	
	// ---------------------
	// | TEXTURES / IMAGES |
	// ---------------------
	
	/** A texture parameter is an extra parameter that can be passed during a
	 * texture's creation to enable certain features.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZETextureParameter { }
	
	/** Texture parameter defining the number of mipmap levels to use with
	 * the texture. Textures created without an explicit number of mipmap
	 * levels will have a single mipmap level.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZETextureParamMipmap implements ZETextureParameter {
		
		/** The number of mipmap levels to use. */
		public final int mipmapLevels;
		
		public ZETextureParamMipmap(int levels) {
			mipmapLevels = levels;
		}
		
	}
	
	/** Texture parameter defining the number of array layers to use
	 * with the texture. Only useful if the texture dimension is one
	 * of {@link ZETextureDimension#DIM_1D_ARRAY DIM_1D_ARRAY},
	 * {@link ZETextureDimension#DIM_2D_ARRAY DIM_2D_ARRAY}, or
	 * {@link ZETextureDimension#CUBE_ARRAY CUBE_ARRAY}.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZETextureParamArray implements ZETextureParameter {
		
		/** The number of array layers to use. */
		public final int arrayLayers;
		
		public ZETextureParamArray(int layers) {
			arrayLayers = layers;
		}
		
	}
	
	/** Creates a texture with the given size and pixel format.
	 * 
	 * @param dim Dimension
	 * @param width Width
	 * @param height Height
	 * @param depth Depth
	 * @param arrayLevels Array level count
	 * @param format Pixel format
	 * @param params Extra texture parameters
	 * @return Texture
	 */
	public ZETexture createTexture(ZETextureDimension dim, int width, int height, int depth,
			ZEPixelFormat format, ZETextureUsage[] usages, ZETextureParameter ... params);
	
	/** Destroys a set of textures.
	 * 
	 * @param textures Textures to destroy
	 */
	public void destroyTextures(ZETexture ... textures);
	
	/** Destroys a set of samplers.
	 * 
	 * @param samplers Samplers to destroy
	 */
	public void destroySamplers(ZESampler ... samplers);
	
	// --------------------------------
	// | COMMAND BUFFERS / SUBMISSION |
	// --------------------------------
	
	/** Creates a new render command buffer.
	 * 
	 * @param multipleRecords If the command buffer can be re-recorded
	 * @return Allocated command buffer
	 */
	public ZERenderCommandBuffer createCommandBuffer(boolean multipleRecords);
	
	/** Multiple version of {@link #createCommandBuffer(boolean)}.
	 * 
	 * @param count Number of buffers to create
	 * @param multipleRecords If the buffers can be re-recorded
	 * @return Allocated command buffers
	 */
	public ZERenderCommandBuffer[] createCommandBuffers(int count, boolean multipleRecords);
	
	/** Destroys a set of render command buffers.
	 * 
	 * @param cmdBuf Command buffers to destroy
	 */
	public void destroyCommandBuffers(ZERenderCommandBuffer ... cmdBuf);
	
	/** Gets the primary render thread, supports all graphics operations.
	 * 
	 * @return Primary render thread
	 */
	public ZERenderThread getPrimaryRenderThread();
	
	// --------------------------
	// | MULTITHREADED COMMANDS |
	// --------------------------
	
	/** Gets the available independent render threads available to this backend.
	 * 
	 * @return Available independent render threads
	 */
	public ZERenderThread[] getRenderThreads();
	
	// --------------------------
	// | INDIRECTION PROPERTIES |
	// --------------------------
	
	/** Indirection parameter for vertex count, unsigned 32-bit integer. */
	public static final String INDIRECTION_PARAM_VERTEX_COUNT = "paramVertexCount";
	/** Indirection parameter for index count, unsigned 32-bit integer. */
	public static final String INDIRECTION_PARAM_INDEX_COUNT = "paramIndexCount";
	/** Indirection parameter for instance count, unsigned 32-bit integer. */
	public static final String INDIRECTION_PARAM_INSTANCE_COUNT = "paramIndexCount";
	/** Indirection parameter for vertex offset, unsigned for non-indexed draw format
	 * and signed for indexed draw format. 32-bit integer in both cases.
	 */
	public static final String INDIRECTION_PARAM_FIRST_VERTEX = "paramFirstVertex";
	/** Indirection parameter for index offset, unsigned 32-bit integer. */
	public static final String INDIRECTION_PARAM_FIRST_INDEX = "paramFirstIndex";
	/** Indirection parameter for instance offset, unsigned 32-bit integer. */
	public static final String INDIRECTION_PARAM_FIRST_INSTANCE = "paramFirstInstance";
	
	/** <p>Gets the parameters used for draw indirection in the order they are
	 * stored in memory. The most common format is as follows:</p>
	 * 
	 * <tt>
	 * <b>struct</b> DrawCommand {<br/>
	 * &emsp;&emsp;uint32_t <i>vertexCount</i>;</br>
	 * &emsp;&emsp;uint32_t <i>instanceCount</i>;</br>
	 * &emsp;&emsp;uint32_t <i>firstVertex</i>;</br>
	 * &emsp;&emsp;uint32_t <i>firstInstance</i>;</br>
	 * }
	 * </tt>
	 * 
	 * @return Indirection parameters in order
	 */
	public default String[] getIndirectionParameters() {
		return new String[] {
			INDIRECTION_PARAM_VERTEX_COUNT,
			INDIRECTION_PARAM_INSTANCE_COUNT,
			INDIRECTION_PARAM_FIRST_VERTEX,
			INDIRECTION_PARAM_FIRST_INSTANCE
		};
	}
	
	/** <p>Similar to {@link #getIndirectionParameters()} but for indexed vertices
	 * with an index buffer. The most common format is as follows:</p>
	 * 
	 * <tt>
	 * <b>struct</b> DrawCommand {<br/>
	 * &emsp;&emsp;uint32_t <i>indexCount</i>;</br>
	 * &emsp;&emsp;uint32_t <i>instanceCount</i>;</br>
	 * &emsp;&emsp;uint32_t <i>firstIndex</i>;</br>
	 * &emsp;&emsp;int32_t <i>vertexOffset</i>;</br>
	 * &emsp;&emsp;uint32_t <i>firstInstance</i>;</br>
	 * }
	 * </tt>
	 * 
	 * @return Indexed indirection parameters in order.
	 */
	public default String[] getIndexedIndirectionParameters() {
		return new String[] {
			INDIRECTION_PARAM_INDEX_COUNT,
			INDIRECTION_PARAM_INSTANCE_COUNT,
			INDIRECTION_PARAM_FIRST_INDEX,
			INDIRECTION_PARAM_FIRST_VERTEX,
			INDIRECTION_PARAM_FIRST_INSTANCE
		};
	}
	
	// ---------------------
	// | CONCURRENCY UTILS |
	// ---------------------
	
	/** Creates a render event.
	 * 
	 * @return Render event
	 */
	public ZERenderEvent createRenderEvent();
	
	/** Destroys a set of render events.
	 * 
	 * @param events Render events
	 */
	public void destroyRenderEvents(ZERenderEvent ... events);
	
	/** Creates a render semaphore.
	 * 
	 * @return Render semaphore
	 */
	public ZERenderSemaphore createRenderSemaphore();
	
	/** Destroys a set of render semaphores.
	 * 
	 * @param semaphores Render semaphores
	 */
	public void destroyRenderSemaphores(ZERenderSemaphore ... semaphores);
	
	/** Creates a render fence.
	 * 
	 * @return Render fence
	 */
	public ZERenderFence createRenderFence();
	
	/** Destroys a set of render fences.
	 * 
	 * @param fences Render fences
	 */
	public void destroyRenderFences(ZERenderFence ... fences);
	
	/** Enumeration of modes for waiting on fences.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEFenceWaitMode {
		/** All fences must be signalled */
		ALL,
		/** Any fence can be signalled */
		ANY
	}
	
	/** Waits for a set of render fences to become signaled.
	 * 
	 * @param waitMode Waiting mode
	 * @param timeout Waiting timeout in nanoseconds
	 * @param fences Set of fences to wait on
	 * @return If the waiting timed out
	 */
	public boolean waitForFences(ZEFenceWaitMode waitMode, long timeout, ZERenderFence ... fences);
	
	/** Resets a set of fences to unsignaled.
	 * 
	 * @param fences Fences to reset
	 */
	public void resetFences(ZERenderFence ... fences);
}
