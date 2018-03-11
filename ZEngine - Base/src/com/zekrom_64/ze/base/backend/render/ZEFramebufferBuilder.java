package com.zekrom_64.ze.base.backend.render;

public interface ZEFramebufferBuilder {
	
	/** Sets the size of the framebuffer.
	 * 
	 * @param width Framebuffer width
	 * @param height Framebuffer height
	 * @param layers Framebuffer layers
	 */
	public void setSize(int width, int height, int layers);
	
	/** Adds a texture as an attachment to the framebuffer.
	 * 
	 * @param tex Texture to attach
	 * @return Attachment index
	 */
	public int addAttachment(ZETexture tex);
	
}
