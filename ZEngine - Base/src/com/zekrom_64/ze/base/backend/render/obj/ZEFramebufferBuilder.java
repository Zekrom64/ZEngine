package com.zekrom_64.ze.base.backend.render.obj;

public interface ZEFramebufferBuilder {
	
	/** Sets the size of the framebuffer.
	 * 
	 * @param width Framebuffer width
	 * @param height Framebuffer height
	 */
	public ZEFramebufferBuilder setSize(int width, int height);
	
	/** Sets the array of attachments to use in the framebuffer.
	 * 
	 * @param textures Textures to use as attachments
	 */
	public ZEFramebufferBuilder setAttachments(ZETexture ... textures);
	
	/** Sets the render pass that defines what render passes the framebuffer will
	 * be compatible with. Render passes are compatible if, for every attachment
	 * mapping, the mappings share the same format and sample count, or are unused. 
	 * 
	 * @param renderPass Render pass
	 */
	public ZEFramebufferBuilder setRenderPass(ZERenderPass renderPass);

	/** Builds a framebuffer.
	 * 
	 * @return Built framebuffer
	 */
	public ZEFramebuffer build();
	
}
