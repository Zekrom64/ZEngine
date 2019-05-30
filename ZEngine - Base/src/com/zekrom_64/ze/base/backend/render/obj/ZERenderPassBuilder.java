package com.zekrom_64.ze.base.backend.render.obj;

import java.util.function.Consumer;

import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEAccessType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;
import com.zekrom_64.ze.base.image.ZEPixelFormat;

/** A render pass builder constructs a render pass.
 * 
 * @author Zekrom_64
 *
 */
public interface ZERenderPassBuilder {

	public enum ZEAttachmentLoadOp {
		/** The previous contents of the attachment are preserved. */
		LOAD,
		/** The attachment is cleared with a value specified at the beginning of a render pass. */
		CLEAR,
		/** The contents of the attachment are undefined. */
		DONT_CARE
	}
	
	public enum ZEAttachmentStoreOp {
		/** Modifications to the attachment are written to memory. */
		STORE,
		/** Modifications may be discarded. */
		DONT_CARE
	}
	
	/** An attachment usage describes how an attachment is accessed by a render pass.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEAttachmentUsage {
		
		/** The format the framebuffer's corresponding attachment must use. */
		public ZEPixelFormat compatibleFormat;
		/** The number 
		public int samples;
		/** The load operation for the color and depth aspects of the attachment at the beginning of a render pass. */
		public ZEAttachmentLoadOp loadOp;
		/** The store operation for the color and depth aspects of the attachment at the end of a render pass. */
		public ZEAttachmentStoreOp storeOp;
		/** The load operation for the stencil aspect of the attachment at the beginning of a render pass. */
		public ZEAttachmentLoadOp stencilLoadOp;
		/** The store operation for the stencil aspect of the attachment at the end of a render pass. */
		public ZEAttachmentStoreOp stencilStoreOp;
		/** The layout of the corresponding attachment at the beginning of a render pass. */
		public ZETextureLayout initialLayout;
		/** The layout of the corresponding attachment at the end of a render pass. */
		public ZETextureLayout finalLayout;
		
	}
	
	/** Sets the attachment usages
	 * 
	 * @param usages
	 */
	public ZERenderPassBuilder setAttachmentUsages(ZEAttachmentUsage ... usages);
	
	/**  A subpass builder maps texture attachments from a framebuffer to specific
	 * attachments used by the graphics pipeline. Passing a <b>null</b> as the
	 * framebuffer attachment index indicates the pipeline attachment is unused. Subpasses
	 * also specifies attachments that may be unused but should preserve their contents
	 * for future usage.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZESubpassBuilder {
		
		/** Maps an input attachment for the subpass.
		 * 
		 * @param index Input attachment index
		 * @param attachmentIndex Index of framebuffer attachment to map to
		 * @param layout Texture layout to use during subpass
		 */
		public ZESubpassBuilder mapInputAttachment(int index, Integer attachmentIndex, ZETextureLayout layout);
		
		/** Maps a color attachment for the subpass.
		 * 
		 * @param index Color attachment index
		 * @param attachmentIndex Index of framebuffer attachment to map to
		 * @param layout Texture layout to use during subpass
		 */
		public ZESubpassBuilder mapColorAttachment(int index, Integer attachmentIndex, ZETextureLayout layout);
		
		/** Maps a depth and/or stencil attachment for the subpass.
		 * 
		 * @param attachmentIndex Index of framebuffer attachment to map to
		 * @param layout Texture layout to use during subpass
		 */
		public ZESubpassBuilder mapDepthStencilAttachment(Integer attachmentIndex, ZETextureLayout layout);
		
		/** Specifies a framebuffer attachment that should not change in the subpass.
		 * 
		 * @param attachmentIndex Index of framebuffer attachment to preserve
		 */
		public ZESubpassBuilder preserveAttachment(int attachmentIndex);
		
	}
	
	/** Adds a subpass to the render pass. This must be called after {@link #setAttachmentUsages}
	 * so that the number of framebuffer attachments is fully defined.
	 * 
	 * @param builder Builder callback for the subpass
	 */
	public ZERenderPassBuilder addSubpass(Consumer<ZESubpassBuilder> builder);
	
	/** Adds a dependency between subpasses in the render pass
	 * 
	 * @param source The index of the subpass defining the completing operations, or <b>null</b> if it is outside the render pass
	 * @param destination The index of the subpass defining the waiting operations, or <b>null</b> if it is outside the render pass
	 * @param completeStages Pipeline stages to wait for completion
	 * @param waitStages Pipeline stages waiting on completion
	 * @param completeAccesses Access to wait for completion
	 * @param waitAccesses Access waiting on completion
	 */
	public ZERenderPassBuilder addSubpassDependency(Integer source, Integer destination, ZEPipelineStage[] completeStages,
			ZEPipelineStage[] waitStages, ZEAccessType[] completeAccesses, ZEAccessType[] waitAccesses);
	
	/** Builds the render pass.
	 * 
	 * @return Render pass
	 */
	public ZERenderPass build();
	
}
