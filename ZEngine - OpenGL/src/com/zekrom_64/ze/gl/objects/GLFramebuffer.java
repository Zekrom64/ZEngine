package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;

public class GLFramebuffer implements ZEFramebuffer {

	/** The default OpenGL framebuffer. The framebuffer doesn't have any explicit attachments,
	 * but usually from creation implicitly has: <ol start="0">
	 * <li>An RGBA single-precision float color attachment</li>
	 * <li>A combined depth and stencil buffer</li>
	 * </ol>
	 * These attachments can be cleared starting a render pass by passing clear values
	 * at their respective indices.
	 */
	public static final GLFramebuffer DEFAULT = new GLFramebuffer(0, new GLTexture[0]);
	
	public final int framebufferObject;
	
	public final GLTexture[] attachments;
	
	public GLFramebuffer(int fbo, GLTexture[] attach) {
		framebufferObject = fbo;
		attachments = attach;
	}
	
}
