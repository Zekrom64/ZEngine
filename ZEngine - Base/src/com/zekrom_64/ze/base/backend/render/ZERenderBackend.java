package com.zekrom_64.ze.base.backend.render;

import java.awt.Rectangle;

import com.zekrom_64.ze.base.backend.render.ZEPipeline.ZEVertexBindPoint;

public interface ZERenderBackend<B extends ZERenderBackend<?>> {
	
	/** The render backend supports multithreaded render work */
	public static final String FEATURE_MULTITHREAD_RENDERING = "ze.feature.multithread.render";
	/** The render backend supports synchronization tools */
	public static final String FEATURE_MULTITHREAD_SYNCHRONIZATION = "ze.features.multithread.synchronization";
	/** The render backend supports multiple pipeline objects */
	public static final String FEATURE_MULTIPLE_PIPELINES = "ze.feature.multi.pipeline";
	/** The render backend supports multiple framebuffer objects */
	public static final String FEATURE_MULTIPLE_FRAMEBUFFERS = "ze.feature.multi.framebuffer";
	/** The render backend supports shader caching */
	public static final String FEATURE_CAHCEABLE_SHADER = "ze.feature.cache.shader";
	/** The render backend supports pipeline caching */
	public static final String FEATURE_CACHEABLE_PIPELINE = "ze.feature.cache.pipeline";
	/** The render backend supports modular shader usage (i.e. a single shader object can be used for
	 * multiple shader stages) */
	public static final String FEATURE_SHADER_MODULAR = "ze.features.shader.modular";
	/** The render backend supports compute shaders that can operate closely with graphics work */
	public static final String FEATURE_SHADER_COMPUTE_AVAILALE = "ze.features.shader.computeavailable";
	/** The render backend supports uniform buffer objects */
	public static final String FEATURE_SHADER_UNIFORM_BUFFER = "ze.features.shader.ubo";
	/** The render backend supports generic usage of device memory */
	public static final String FEATURE_GENERIC_MEMORY = "ze.features.generic.memory";
	/** The render backend supports generic usage of images */
	public static final String FEATURE_GENERIC_IMAGE = "ze.features.generic.image";
	/** The render backend supports generic usage of memory buffers */
	public static final String FEATURE_GENERIC_BUFFER = "ze.features.generic.buffer";

	/** Tests if the render backend supports a given feature. If the given feature string is not
	 * recognized, false will be returned.
	 * 
	 * @param feature The feature to test for
	 * @return If the feature is available
	 */
	public boolean supportsFeature(String feature);
	
	/** Initializes the render backend with information provided by the application, and begins outputting
	 * to the given render output. This will only be called once internally by the engine when the ZEApplication
	 * is instantiated.
	 */
	public void init(ZERenderOutput<B> backend);
	
	/** Deinitializes the render backend with information provided by the application. This will only be called
	 * once internally by the engine when the ZEApplication finishes executing.
	 */
	public void deinit();
	
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
	
	/** Render work is a generic unit of work that may be performed by a render backend. Render work may be
	 * instantiated using an implementation of {@link ZERenderWorkFactory} provided by a render backend.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZERenderWork { }
	
	/** A compound render work object wraps multiple components of render work into a single render work object.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZECompoundRenderWork extends ZERenderWork {
		
		/** Gets the components that make up this render work.
		 * 
		 * @return
		 */
		public ZERenderWork[] getComponents();
		
	}
	
	/** A render work factory abstracts the implementation specifics of render work.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZERenderWorkFactory {
		
		/** Compiles render work into a single unit. Compiled render work is faster than 
		 * 
		 * @param work
		 * @return
		 */
		public ZERenderWork compile(ZERenderWork work);
		
		/** Creates an inline runnable invocation in the render work. This should be used as little as possible, as
		 * for command buffer based backends such as Vulkan this breaks command buffer recording into separate buffers
		 * and incurs synchronization overheads.
		 * 
		 * @param r Runnable to invoke
		 * @return Render work
		 */
		public ZERenderWork inlineWork(Runnable r);
		
		public ZECompoundRenderWork compoundWork(ZERenderWork[] components);
		
		/** Creates render work to bind a pipeline to the render backed. If the render backend does not support multiple
		 * pipeline objects, this will be processed 
		 * 
		 * @param pipeline Pipeline to bind
		 * @return
		 */
		public ZERenderWork bindPipeline(ZEPipeline pipeline);
		
		/** Creates render work to set the scissor tests for 
		 * 
		 * @param scissors
		 * @param firstScissor
		 * @return
		 */
		public ZERenderWork setScissor(Rectangle[] scissors, int firstScissor);
		
		/** Creates render work to change the line width of rendered lines.
		 * 
		 * @param width New line width
		 * @return Render work
		 */
		public ZERenderWork setLineWidth(float width);
		
		/** Creates render work to change the depth bounds.
		 * 
		 * @param min Minimum bounds
		 * @param max Maximum bounds
		 * @return Render work
		 */
		public ZERenderWork setDepthBounds(double min, double max);
		
		/** Creates render work to bind a vertex buffer to a vertex binding point in the pipeline.
		 * 
		 * @param bindPoint
		 * @param buffer
		 * @return
		 */
		public ZERenderWork bindVertexBuffer(ZEVertexBindPoint bindPoint, ZEVertexBuffer buffer);
		
		/** Creates render work to bind an index buffer for rendering.
		 * 
		 * @param buffer Index buffer
		 * @return Render work
		 */
		public ZERenderWork bindIndexBuffer(ZEIndexBuffer buffer);
		
		/** Creates render work to draw a series of vertices from any bound vertex buffers.
		 * 
		 * @param nVertices Number of vertices to draw
		 * @param start Index of starting vertex
		 * @return Render work
		 */
		public ZERenderWork draw(int nVertices, int start);
		
		/** Creates render work to draw a series of vertices from any bound vertex buffers using a bound index buffer.
		 * 
		 * @param nIndices Number of vertices to draw
		 * @param startIndex Index of starting index
		 * @param startVertex Index of starting vertex
		 * @return Render work
		 */
		public ZERenderWork drawIndexed(int nIndices, int startIndex, int startVertex);
		
		/** Creates render work to set an event.
		 * 
		 * @param event Render event to set
		 * @return Render work
		 */
		public ZERenderWork setEvent(ZERenderEvent event);
		
		/** Creates render work to reset an event.
		 * 
		 * @param event Render event to reset
		 * @return Render work
		 */
		public ZERenderWork resetEvent(ZERenderEvent event);
		
		/** Creates render work to perform a block transfer from one buffer to another.
		 * 
		 * @param src Source buffer
		 * @param dst Destination buffer
		 * @param srcPos Source position
		 * @param dstPos Destination position
		 * @param size Block size
		 * @return Render work
		 */
		public ZERenderWork blitBuffer(ZEBuffer src, ZEBuffer dst, int srcPos, int dstPos, int size);
		
	}
	
	/** Gets the factory for creating backend compatible render work.
	 * 
	 * @return Render work factory
	 */
	public ZERenderWorkFactory getWorkFactory();
	
}
