package com.zekrom_64.ze.base.backend.render;

import java.nio.ByteBuffer;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEColorClearValue;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureRange;
import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEAccessType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEVertexInput;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEViewport;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;

/** A render work recorder implements a way to record a series of commands to be
 * issued to a render backend. Values provided to record commands can be safely
 * modified after using them in the recorder, but cannot be modified concurrently
 * while being recorded.
 * 
 * @author Zekrom_64
 *
 */
public interface ZERenderWorkRecorder {
	
	// ------[Execution]------
	
	/** Executes the commands in a render work buffer. The render backend may or
	 * may not support multilevel calls with render work buffers.
	 * 
	 * @param buffer Work buffer to execute
	 * @return Render work
	 */
	public void executeBuffer(ZERenderCommandBuffer buffer);
	
	// ------[Pipeline State]------
	
	/** Binds a pipeline to the render backed.
	 * 
	 * @param pipeline Pipeline to bind
	 */
	public void bindPipeline(ZEPipeline pipeline);
	
	public static class ZEAttachmentClearValue {
		
		/** The color clear value. */
		public final ZEColorClearValue color = new ZEColorClearValue();
		/** The depth clear value. */
		public float depth = 0;
		/** The stencil clear value. */
		public int stencil = 0;
		
	}
	
	/** Begins a render pass using the given framebuffer.
	 * 
	 * @param bindSet Bind set to use
	 * @param framebuffer Framebuffer to render to
	 * @param clearValues Clear values for framebuffer attachments
	 */
	public void beginPass(ZERenderPass renderPass, ZEFramebuffer framebuffer, ZEAttachmentClearValue[] clearValues);
	
	/** Goes to the next pass in the current render pass.
	 * 
	 */
	public void nextPass();
	
	/** Finishes a render pass.
	 * 
	 */
	public void endPass();
	
	// ------[Dynamic State]------
	
	/** Sets the scissor tests for the current pipeline. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_SCISSOR
	 * DYNAMIC_STATE_SCISSOR} dynamic state enabled.
	 * 
	 * @param scissors Scissor areas
	 * @param firstScissor Index of first scissor
	 * @param numScissors Number of scissors to set
	 */
	public void setScissor(Rectangle[] scissors, int firstScissor, int numScissors);
	
	/** Sets the viewports of the current pipeline. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_VIEWPORT
	 * DYNAMIC_STATE_VIEWPORT} dynamic state enabled.
	 * 
	 * @param viewports Viewports
	 * @param firstViewport Index of the first viewport
	 */
	public void setViewport(ZEViewport[] viewports, int firstViewport);
	
	/** Changes the line width of rendered lines. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_LINE_WIDTH
	 * DYNAMIC_STATE_LINE_WIDTH} dynamic state enabled.
	 * 
	 * @param width New line width
	 */
	public void setLineWidth(float width);
	
	/** Changes the depth bias settings. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_DEPTH_BIAS
	 * DYNAMIC_STATE_DEPTH_BIAS} dynamic state enabled.
	 * 
	 * @param constantFactor Constant value added to fragment's depth
	 * @param clamp Maximum (or minimum) depth bias of a fragment
	 * @param slopeFactor Constant value added to fragment's slope
	 */
	public void setDepthBias(double constantFactor, double clamp, double slopeFactor);
	
	/** Changes the color blending constant. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_BLEND_CONSTANTS
	 * DYNAMIC_STATE_BLEND_CONSTANTS} dynamic state enabled.
	 * 
	 * @param constants Array of red, green, blue, and alpha constants
	 */
	public void setBlendConstants(float ... constants);
	
	/** Changes the depth bounds. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_DEPTH_BOUNDS
	 * DYNAMIC_STATE_DEPTH_BOUNDS} dynamic state enabled.
	 * 
	 * @param min Minimum bounds
	 * @param max Maximum bounds
	 */
	public void setDepthBounds(double min, double max);
	
	/** Changes the stencil compare mask. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_STENCIL_COMPARE_MASK
	 * DYNAMIC_STATE_STENCIL_COMAPRE_MASK} dynamic state enabled.
	 * 
	 * @param face Set of stencil states to update
	 * @param compareMask New compare mask value
	 */
	public void setStencilCompareMask(ZEFrontBack face, int compareMask);
	
	/** Changes the stencil wire mask. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_STENCIL_WRITE_MASK
	 * DYNAMIC_STATE_STENCIL_WRITE_MASK} dynamic state enabled.
	 * 
	 * @param face Set of stencil states to update
	 * @param writeMask New write mask value
	 */
	public void setStencilWriteMask(ZEFrontBack face, int writeMask);
	
	/** Changes the stencil reference value. Requires the current pipeline to have the
	 * {@link com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder#DYNAMIC_STATE_STENCIL_REFERENCE
	 * DYNAMIC_STATE_STENCIL_REFERENCE} dynamic state enabled.
	 * 
	 * @param face Set of stencil states to update
	 * @param reference New reference value
	 */
	public void setStencilReference(ZEFrontBack face, int reference);
	
	// ------[Input Binding]------
	
	/** Binds a pipeline bind set to the current pipeline.
	 * 
	 * @param bindSet Pipeline bind set to bind
	 */
	public void bindPipelineBindSet(ZEPipelineBindSet bindSet);
	
	/** Binds a vertex buffer to a vertex input in the pipeline.
	 * 
	 * @param bindPoint Vertex input point
	 * @param buffer Vertex buffer to bind
	 */
	public void bindVertexBuffer(ZEVertexInput bindPoint, ZEVertexBuffer buffer);
	
	/** Binds several vertex buffers to a vertex input in the pipeline.
	 * 
	 * @param firstBindPoint The starting binding point to use
	 * @param buffers Vertex buffers to bind
	 */
	public void bindVertexBuffers(ZEVertexInput firstBindPoint, ZEVertexBuffer ... buffers);
	
	/** Binds an index buffer for rendering.
	 * 
	 * @param buffer Index buffer
	 */
	public void bindIndexBuffer(ZEIndexBuffer buffer);
	
	// ------[Rendering]------
	
	/** Draws a series of vertices from any bound vertex buffers. This command can only be called
	 * between calls of {@link #beginPass} or {@link #nextPass}, and {@link #endPass}.
	 * 
	 * @param nVertices Number of vertices to draw
	 * @param start Index of starting vertex
	 */
	public void draw(int nVertices, int start);
	
	/** Draws a series of vertices from any bound vertex buffers using a bound index buffer.
	 * This command can only be called between calls of {@link #beginPass} or
	 * {@link #nextPass}, and {@link #endPass}.
	 * 
	 * @param nIndices Number of vertices to draw
	 * @param startIndex Index of starting index
	 * @param startVertex Index of starting vertex
	 */
	public void drawIndexed(int nIndices, int startIndex, int startVertex);
	
	/** <p>Draws a series of vertices from any bound vertex buffers using the given buffer as
	 * a source for the draw information. The data is stored in the buffer as an array
	 * of structures described by {@link ZERenderBackend#getIndirectionParameters()}.</p>
	 * 
	 * <p> This command can only be called between calls of {@link #beginPass} or
	 * {@link #nextPass}, and {@link #endPass}. </p>
	 * 
	 * @param paramBuffer Indirection buffer
	 * @param offset Offset into buffer in bytes
	 * @param drawCount Number of draws to execute
	 * @param stride Stride in bytes between entries
	 */
	public void drawIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride);
	
	/** <p>Draws a series of vertices from any bound vertex buffers using a bound index buffer, with
	 * the given buffer used as a source for the draw information. The data is stored in the buffer
	 * as an array of structures described by {@link ZERenderBackend#getIndexedIndirectionParameters()}.</p>
	 * 
	 * <p> This command can only be called between calls of {@link #beginPass} or
	 * {@link #nextPass}, and {@link #endPass}. </p>
	 * 
	 * @param paramBuffer Indirection buffer
	 * @param offset Offset into buffer in bytes
	 * @param drawCount Number of draws to execute
	 * @param stride Stride in bytes between entries
	 */
	public void drawIndexedIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride);
	
	// ------[Synchronization]------
	
	/** Sets an event. Waits until all previous commands have finished
	 * the given stage to signal the event.
	 * 
	 * @param stage Stage to wait on to set event
	 * @param event Render event to set
	 */
	public void setEvent(ZEPipelineStage stage, ZERenderEvent event);
	
	/** Resets an event. Waits until all previous commands have finished
	 * the given stage to unsignal the event.
	 * 
	 * @param stage Stage to wait on to reset event
	 * @param event Render event to reset
	 */
	public void resetEvent(ZEPipelineStage stage, ZERenderEvent event);
	
	/** A barrier defines a memory dependency between different uses of the same
	 * resource in a single render work submission. Barriers can change some
	 * properties of a resource between these uses when synchronized.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZEPipelineBarrier { }
	
	/** A texture barrier defines a memory dependency for a single texture.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZETextureBarrier implements ZEPipelineBarrier {
		
		public final ZETexture texture;
		
		/** The set of access types to finish before the barrier. */
		public ZEAccessType[] finishAccesses;
		/** The set of access types that must wait on the barrier. */
		public ZEAccessType[] waitAccesses;
		
		/** Image layout before barrier. */
		public ZETextureLayout oldLayout;
		/** New image layout after barrier. */
		public ZETextureLayout newLayout;
		
		/** Owning render thread before barrier. */
		public ZERenderThread oldThread;
		/** Owning render thread after barrier. */
		public ZERenderThread newThread;
		
		/** Range of the texture to barrier. */
		public ZETextureRange range;
		
		public ZETextureBarrier(ZETexture tex, ZEAccessType[] finish, ZEAccessType[] wait) {
			texture = tex;
			finishAccesses = finish;
			waitAccesses = wait;
		}
		
		public ZETextureBarrier changeLayout(ZETextureLayout old, ZETextureLayout _new) {
			oldLayout = old;
			newLayout = _new;
			return this;
		}
		
		public ZETextureBarrier changeThread(ZERenderThread old, ZERenderThread _new) {
			oldThread = old;
			newThread = _new;
			return this;
		}
		
	}
	
	/** A buffer barrier defines a memory dependency for a single buffer.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEBufferBarrier implements ZEPipelineBarrier {
		
		public final ZEBuffer buffer;
		
		/** The set of access types to finish before the barrier. */
		public ZEAccessType[] finishAccesses;
		/** The set of access types that must wait on the barrier. */
		public ZEAccessType[] waitAccesses;
		
		/** Owning render thread before barrier. */
		public ZERenderThread oldThread;
		/** Owning render thread after barrier. */
		public ZERenderThread newThread;
		
		/** Offset into the buffer for the barrier region. */
		public long offset = 0;
		/** Size of the barrier region. */
		public long size = Long.MAX_VALUE;
		
		public ZEBufferBarrier(ZEBuffer buf, ZEAccessType[] finish, ZEAccessType[] wait) {
			buffer = buf;
			finishAccesses = finish;
			waitAccesses = wait;
		}
		
		public ZEBufferBarrier changeThread(ZERenderThread old, ZERenderThread _new) {
			oldThread = old;
			newThread = _new;
			return this;
		}
		
	}
	
	/** A global barrier defines a global memory dependency.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEGlobalBarrier implements ZEPipelineBarrier {
		
		/** The set of access types to finish before the barrier. */
		public ZEAccessType[] finishAccesses;
		/** The set of access types that must wait on the barrier. */
		public ZEAccessType[] waitAccesses;
		
		public ZEGlobalBarrier(ZEAccessType[] finish, ZEAccessType[] wait) {
			finishAccesses = finish;
			waitAccesses = wait;
		}
		
	}
	
	/** Waits for a set of render events to become signaled before continuing execution. The
	 * set of completing stages define the latest stage to allow earlier {@link #setEvent} commands to
	 * affect the waiting, and define the pipeline stages that waiting for barrier access completion
	 * will apply to. The set of waiting stages define pipeline stages that must wait for the events
	 *  to complete, and define the stages any barrier access waiting will apply to.
	 * 
	 * @param completeStages Stages to complete event signaling and barriers
	 * @param waitingStages Stages waiting on event signaling and barriers
	 * @param events Events to wait on
	 * @param barriers Barriers to wait on
	 */
	public void waitForEvents(ZEPipelineStage[] completeStages, ZEPipelineStage[] waitingStages, ZERenderEvent[] events, ZEPipelineBarrier ... barriers);
	
	/** Version of {@link #waitForEvents(ZEPipelineStage[], ZEPipelineStage[], ZERenderEvent[], ZEPipelineBarrier...)}
	 * that defines no barriers.
	 * 
	 * @param completeStages Stages to complete event signaling and barriers
	 * @param waitingStages Stages waiting on event signaling and barriers
	 * @param events Events to wait on
	 */
	public default void waitForEvents(ZEPipelineStage[] completeStages, ZEPipelineStage[] waitingStages, ZERenderEvent ... events) {
		waitForEvents(completeStages, waitingStages, events, new ZEPipelineBarrier[0]);
	}
	
	/** Creates a pipeline barrier including previous commands in any of the completing stages, and waits for access defined
	 * in the barriers before allowing accesses in the waiting stages to occur.
	 * 
	 * @param completeStages Stages to complete finishing barrier accesses
	 * @param waitingStages Stages to block waiting barrier accesses
	 * @param barriers Pipeline barriers
	 */
	public void pipelineBarrier(ZEPipelineStage[] completeStages, ZEPipelineStage[] waitingStages, ZEPipelineBarrier ... barriers);
	
	// ------[Memory Operations]------
	
	/** Performs a block transfer from one buffer to another.
	 * 
	 * @param src Source buffer
	 * @param dst Destination buffer
	 * @param srcPos Source position
	 * @param dstPos Destination position
	 * @param size Block size
	 */
	public void blitBuffer(ZEBuffer src, ZEBuffer dst, int srcPos, int dstPos, int size);
	
	/** Performs a block transfer from one texture to another.
	 * 
	 * @param srcTex Source texture
	 * @param dstTex Destination texture
	 * @param src Source position
	 * @param dst Destination position
	 * @param size Transfer size
	 */
	public void blitTexture(ZETexture srcTex, ZETexture dstTex, Vector3I src, Vector3I dst, Vector3I size);
	
	/** Clears a buffer region to the given value. Some backends might optimize this depending on
	 * alignment and value size (ex. Vulkan backends will use vkCmdFillBuffer if the command
	 * can be done equivalently with it). While the offset can be a non-multiple of the value size,
	 * this is not recommended as it is usually unoptimized and done by CPU-side memory access.
	 * 
	 * @param buf Buffer to clear
	 * @param offset Byte offset into buffer
	 * @param count Number of values to clear
	 * @param value Value to clear with
	 */
	public void clearBuffer(ZEBuffer buf, int offset, int count, ByteBuffer value);
	
	/** Clears a texture region to the given color. Some backends will optimize this depending on
	 * the fill area (if it is the full texture or not).
	 * 
	 * @param tex Texture to clear
	 * @param start Clear region start
	 * @param size Clear region size
	 * @param range Clear region range
	 * @param color Clear value
	 */
	public void clearTexture(ZETexture tex, Vector3I start, Vector3I size, ZETextureRange range, ZEColorClearValue color);
	
	/** Transfers data from an image to a buffer. Some backends will optimize this into a native
	 * command or optimize it depending on the image area and buffer segment.
	 * 
	 * @param src Source texture
	 * @param srcPos Source position
	 * @param srcSize Source size
	 * @param srcRange Source range
	 * @param dst Destination buffer
	 * @param dstOffset Destination offset
	 * @param dstRowLength Row length in the buffer in texels
	 * @param dstHeight Height length in the buffer in texels
	 */
	public void imageToBuffer(ZETexture src, Vector3I srcPos, Vector3I srcSize, ZETextureRange srcRange, ZEBuffer dst, int dstOffset, int dstRowLength, int dstHeight);
	
	/** Transfers data from a buffer to an image. Some backends will optimize this into a native
	 * command or optimize it depending on the image area and buffer segment.
	 * 
	 * @param src Source buffer
	 * @param srcOffset Source offset
	 * @param srcRowLength Row length in the buffer in texels
	 * @param srcHeight Height length in the buffer in texels
	 * @param dst Destination texture
	 * @param dstPos Destination position
	 * @param dstSize Destination size
	 * @param dstRange Destination range
	 */
	public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst, Vector3I dstPos, Vector3I dstSize, ZETextureRange dstRange);
	
	/** Uploads data to a render backend buffer from a local buffer. Some backends may have
	 * limits on the size of data uploaded.
	 * 
	 * @param dst Destination buffer
	 * @param dstoff Destination offset
	 * @param len Length in bytes to upload
	 * @param data Source buffer
	 */
	public void uploadToBuffer(ZEBuffer dst, int dstoff, int len, ByteBuffer data);
	
	/** Transitions the layout of an image from an old layout to a new layout.
	 * 
	 * @param tex Texture to transition
	 * @param oldLayout Old texture layout
	 * @param newLayout New texture layout
	 */
	public void transitionTextureLayout(ZETexture tex, ZETextureLayout oldLayout, ZETextureLayout newLayout);
	
	// ------[Presentation]------
	
}