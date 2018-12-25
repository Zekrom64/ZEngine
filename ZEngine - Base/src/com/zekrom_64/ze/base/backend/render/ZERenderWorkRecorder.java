package com.zekrom_64.ze.base.backend.render;

import java.nio.ByteBuffer;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.tuple.impl.Vector3Int;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEVertexInput;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEViewport;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;
import com.zekrom_64.ze.base.image.ZEPixelFormat;

/** A render work factory abstracts the implementation specifics of render work.
 * 
 * @author Zekrom_64
 *
 */
public interface ZERenderWorkRecorder {
	
	// ------[Execution]------
	
	/** Creates an inline runnable invocation in the render work. This should be used as little as possible, as
	 * for command buffer based backends such as Vulkan this incurs synchronization overheads.
	 * 
	 * @param r Runnable to invoke
	 * @deprecated CPU-side code should not be mixed with GPU-side work
	 */
	@Deprecated
	public void inlineWork(Runnable r);
	
	/** Executes the commands in a render work buffer. The render backend may or
	 * may not support multilevel calls with render work buffers.
	 * 
	 * @param buffer Work buffer to execute
	 * @return Render work
	 */
	public void executeBuffer(ZERenderCommandBuffer buffer);
	
	// ------[Pipeline State]------
	
	/** Binds a pipeline to the render backed. If the render backend does not support multiple
	 * pipeline objects, this will be processed 
	 * 
	 * @param pipeline Pipeline to bind
	 */
	public void bindPipeline(ZEPipeline pipeline);
	
	/** Binds a binding set of attachments to begin a render pass.
	 * 
	 * @param bindSet Bind set to use
	 */
	public void beginPass(ZEPipelineBindSet bindSet, ZEFramebuffer framebuffer);
	
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
	 * @param numViewports Number of viewports to set
	 */
	public void setViewport(ZEViewport[] viewports, int firstViewport, int numViewports);
	
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
	
	/** Binds a vertex buffer to a vertex input in the pipeline.
	 * 
	 * @param bindPoint Vertex input point
	 * @param buffer Vertex buffer to bind
	 */
	public void bindVertexBuffer(ZEVertexInput bindPoint, ZEVertexBuffer buffer);
	
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
	
	/** Sets an event.
	 * 
	 * @param event Render event to set
	 */
	public void setEvent(ZEPipelineStage stage, ZERenderEvent event);
	
	/** Resets an event.
	 * 
	 * @param event Render event to reset
	 */
	public void resetEvent(ZEPipelineStage stage, ZERenderEvent event);
	
	public void waitForEvents(ZEPipelineStage[] signalStages, ZEPipelineStage[] waitingStages, ZERenderEvent ... events);
	
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
	public void blitTexture(ZETexture srcTex, ZETexture dstTex, Vector3Int src, Vector3Int dst, Vector3Int size);
	
	/** Clears a buffer region to the given value. Some backends might optimize this depending on
	 * alignment and value size (ex. Vulkan backends will use vkCmdFillBuffer if the offset is a
	 * multiple of 4 or zero, and the value size is 4).
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
	 * @param format Pixel format for clear value
	 * @param color Clear value
	 */
	public void clearTexture(ZETexture tex, Vector3Int start, Vector3Int size, ZEPixelFormat format, ByteBuffer color);
	
	/** Transfers data from an image to a buffer. Some backends will optimize this into a native
	 * command or optimize it depending on the image area and buffer segment.
	 * 
	 * @param src Source texture
	 * @param srcPos Source position
	 * @param srcSize Source image size
	 * @param dst Destination buffer
	 * @param dstOffset Destination offset
	 * @param dstRowLength Row length in the buffer in bytes
	 * @param dstHeight Height length in the buffer in bytes
	 */
	public void imageToBuffer(ZETexture src, Vector3Int srcPos, Vector3Int srcSize, ZEBuffer dst, int dstOffset, int dstRowLength, int dstHeight);
	
	/** Transfers data from a buffer to an image. Some backends will optimize this into a native
	 * command or optimize it depending on the image area and buffer segment.
	 * 
	 * @param src Source buffer
	 * @param srcOffset Source offset
	 * @param srcRowLength Row length in the buffer in bytes
	 * @param srcHeight Height length in the buffer in bytes
	 * @param dst Destination texture
	 * @param dstPos Destination position
	 * @param dstSize Destination size
	 */
	public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst, Vector3Int dstPos, Vector3Int dstSize);
	
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
	
}