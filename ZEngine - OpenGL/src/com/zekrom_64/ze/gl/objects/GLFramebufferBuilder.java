package com.zekrom_64.ze.gl.objects;

import java.util.Arrays;

import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebufferBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.gl.GLNativeContext;
import com.zekrom_64.ze.gl.GLRenderBackend;

public class GLFramebufferBuilder implements ZEFramebufferBuilder {

	private final GLRenderBackend backend;
	private final GLNativeContext context;
	
	public GLFramebufferBuilder(GLRenderBackend backend) {
		this.backend = backend;
		context = backend.getNativeContext();
	}
	
	private GLTexture[] attachments;
	
	@Override
	public ZEFramebufferBuilder setSize(int width, int height) {
		return this; // Technically doesn't matter
	}

	@Override
	public ZEFramebufferBuilder setAttachments(ZETexture... textures) {
		attachments = Arrays.copyOf(textures, textures.length, GLTexture[].class);
		return this;
	}

	@Override
	public ZEFramebufferBuilder setRenderPass(ZERenderPass renderPass) {
		return this; // OpenGL doesn't care about your damn render pass compatibility
	}

	@Override
	public ZEFramebuffer build() {
		context.bindExclusively();
		try {
			int fb = backend.getExtensions().glGenFramebuffers();
			backend.checkErrorFine();
			backend.checkErrorCoarse("Failed to create framebuffer");
			return new GLFramebuffer(fb, attachments);
		} finally {
			context.unbindExclusively();
		}
	}

}
