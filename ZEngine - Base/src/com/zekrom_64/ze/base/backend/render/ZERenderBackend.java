package com.zekrom_64.ze.base.backend.render;

import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.util.PrimitiveType;

public interface ZERenderBackend<B extends ZERenderBackend<?>> {
	
	// -------------------
	// | FEATURE STRINGS |
	// -------------------
	
	// Multithreading / Multi-object
	
	/** The render backend supports multithreaded render work */
	public static final String FEATURE_MULTITHREAD_RENDERING = "ze.feature.multithread.render";
	/** The render backend supports synchronization tools */
	public static final String FEATURE_MULTITHREAD_SYNCHRONIZATION = "ze.features.multithread.synchronization";
	/** The render backend supports multiple pipeline objects */
	public static final String FEATURE_MULTIPLE_PIPELINES = "ze.feature.multi.pipeline";
	/** The render backend supports multiple framebuffer objects */
	public static final String FEATURE_MULTIPLE_FRAMEBUFFERS = "ze.feature.multi.framebuffer";
	
	// Render work features
	
	/** The render backend supports multilevel render work buffer recording */
	public static final String FEATURE_MULTILEVEL_RENDER_WORK_BUFFER = "ze.features.renderWork.multilevelBuffer";
	
	// Pipeline features
	
	/** The render backend supports pipeline caching */
	public static final String FEATURE_CACHEABLE_PIPELINE = "ze.feature.cache.pipeline";
	
	// Shader features
	
	/** The render backend supports shader caching */
	public static final String FEATURE_CAHCEABLE_SHADER = "ze.feature.cache.shader";
	/** The render backend supports modular shader usage (i.e. a single shader object can be used for
	 * multiple shader stages) */
	public static final String FEATURE_SHADER_MODULAR = "ze.features.shader.modular";
	/** The render backend supports compute shaders that can operate closely with graphics work */
	public static final String FEATURE_SHADER_COMPUTE_AVAILALE = "ze.features.shader.computeavailable";
	/** The render backend supports uniform buffer objects */
	public static final String FEATURE_SHADER_UNIFORM_BUFFER = "ze.features.shader.ubo";
	
	// Object features
	
	/** The render backend supports generic usage of device memory */
	public static final String FEATURE_GENERIC_MEMORY = "ze.features.generic.memory";
	/** The render backend supports generic usage of images */
	public static final String FEATURE_GENERIC_IMAGE = "ze.features.generic.image";
	/** The render backend supports generic usage of memory buffers */
	public static final String FEATURE_GENERIC_BUFFER = "ze.features.generic.buffer";
	
	// Drawing features

	/** The render backend supports commands for indirect drawing */
	public static final String FEATURE_COMMAND_DRAW_INDIRECT = "ze.features.command.indirectDraw";
	
	// Texture features
	
	/** The render backend supports array texture cubemaps */
	public static final String FEATURE_CUBEMAP_ARRAY = "ze.features.texture.arrayCubemap";
	/** The render backend supports array textures */
	public static final String FEATURE_TEXTURE_ARRAY = "ze.features.texture.array";

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
	
	// -------------------
	// | INIT AND DEINIT |
	// -------------------
	
	/** Initializes the render context. */
	public void init(ZERenderContext<B> context);
	
	/** Deinitializes the render backend. */
	public void deinit();
	
	// -------------
	// | PIPELINES |
	// -------------
	
	/** Gets any predefined or default pipelines defined by the render backend. If the backend uses multiple
	 * pipeline objects, this returns null.
	 * 
	 * @return Default pipeline
	 */
	public ZEPipeline getDefaultPipeline();
	
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
	
	// -----------
	// | SHADERS |
	// -----------
	
	/** Gets the compiler for compiling compatible shaders.
	 * 
	 * @return Shader compiler
	 */
	public ZEShaderCompiler getShaderCompiler();
	
	// ------------------
	// | MEMORY BUFFERS |
	// ------------------
	
	/** Buffer flag specifying that the buffer should be device local. Not all backends support this flag. */
	public static final int FLAG_BUFFER_DEVICE_LOCAL = 0b1;
	
	/** Allocates a memory buffer. Buffers may be given flags to modify their behavior. If a flag is not
	 * supported, it will either fail silently or throw an exception.
	 * 
	 * @param size Buffer size
	 * @param flags Buffer flags
	 * @return Memory buffer
	 */
	public ZEBuffer allocateBuffer(int size, int flags);
	
	/** Multiple allocation version of {@link #allocateBuffer(int, int) allocateBuffer()}.
	 * 
	 * @param sizes Buffer sizes
	 * @param flags Buffer flags
	 * @return Memory buffers
	 */
	public ZEBuffer[] allocateBuffers(int[] sizes, int[] flags);
	
	/** Frees a memory buffer.
	 * 
	 * @param buffer Memory buffer
	 */
	public void freeBuffer(ZEBuffer buffer);
	
	/** Multiple free version of {@link #freeBuffer(ZEBuffer) freeBuffer()}.
	 * 
	 * @param buffers Memory buffers
	 */
	public void freeBuffer(ZEBuffer ... buffers);
	
	/** Creates a vertex buffer from a memory buffer.
	 * 
	 * @param buffer Memory buffer
	 * @return Vertex buffer
	 */
	public ZEVertexBuffer createVertexBuffer(ZEBuffer buffer);
	
	/** Creates an index buffer from a memory buffer
	 * 
	 * @param buffer Memory buffer
	 * @param indexType 
	 * @return
	 */
	public ZEIndexBuffer createIndexBuffer(ZEBuffer buffer, PrimitiveType indexType);
	
	// ---------------------
	// | TEXTURES / IMAGES |
	// ---------------------
	
	/** Creates a texture with the given size and pixel format.
	 * 
	 * @param dim Dimension
	 * @param width Width
	 * @param height Height
	 * @param depth Depth
	 * @param format Pixel format
	 * @return Texture
	 */
	public ZETexture createTexture(ZETextureDimension dim, int width, int height, int depth, ZEPixelFormat format);
	
	/** Multiple creation version of {@link #createTexture(int, int, ZEPixelFormat) createTexture()}.
	 * 
	 * @param dim Dimensions
	 * @param width Widths
	 * @param height Heights
	 * @param depth Depths
	 * @param format Pixel formats
	 * @return Textures
	 */
	public ZETexture[] createTextures(ZETextureDimension[] dim, int[] width, int[] height, int[] depth, ZEPixelFormat[] format);
	
	/** Destroys a texture.
	 * 
	 * @param texture Texture
	 */
	public void destroyTexture(ZETexture texture);
	
	/** Multiple version of {@link #destroyTexture(ZETexture) destroyTexture()}.
	 * 
	 * @param textures
	 */
	public void destroyTexture(ZETexture ... textures);
	
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
	
	/** Destroys a render command buffer.
	 * 
	 * @param cmdBuf Command buffer to destroy
	 */
	public void destroyCommandBuffer(ZERenderCommandBuffer cmdBuf);
	
	/** Multiple version of {@link #destroyCommandBuffer(ZERenderCommandBuffer)}.
	 * 
	 * @param cmdBufs Command buffers to destroy
	 */
	public void desotryCommandBuffers(ZERenderCommandBuffer ... cmdBufs);
	
	/** Submits a command buffer for execution.
	 * 
	 * @param cmdBuf Command buffer
	 */
	public void submitCommands(ZERenderCommandBuffer cmdBuf);
	
	// --------------------------
	// | MULTITHREADED COMMANDS |
	// --------------------------
	
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
	
	public ZERenderSemaphore createRenderSemaphore();
}
