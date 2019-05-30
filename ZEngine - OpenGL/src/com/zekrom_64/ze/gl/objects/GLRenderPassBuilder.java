package com.zekrom_64.ze.gl.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEAccessType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;
import com.zekrom_64.ze.gl.objects.GLRenderPass.GLSubpass;

public class GLRenderPassBuilder implements ZERenderPassBuilder {

	private ZEAttachmentUsage[] usages;
	private List<GLSubpass> subpasses = new ArrayList<>();
	
	@Override
	public ZERenderPassBuilder setAttachmentUsages(ZEAttachmentUsage... usages) {
		this.usages = usages;
		return this;
	}
	
	private class GLSubpassBuilder implements ZESubpassBuilder {

		private GLSubpass subpass;
		
		@Override
		public ZESubpassBuilder mapInputAttachment(int index, Integer attachmentIndex, ZETextureLayout layout) {
			subpass.inputAttachments[index] = attachmentIndex;
			return null;
		}

		@Override
		public ZESubpassBuilder mapColorAttachment(int index, Integer attachmentIndex, ZETextureLayout layout) {
			subpass.colorAttachments[index] = attachmentIndex;
			return null;
		}

		@Override
		public ZESubpassBuilder mapDepthStencilAttachment(Integer attachmentIndex, ZETextureLayout layout) {
			subpass.depthStencilAttachment = attachmentIndex;
			return null;
		}

		@Override
		public ZESubpassBuilder preserveAttachment(int attachmentIndex) {
			return this; // OpenGL doesn't randomly trash attachments because it feels like it
		}
		
	}
	
	@Override
	public ZERenderPassBuilder addSubpass(Consumer<ZESubpassBuilder> builder) {
		GLSubpassBuilder subBuilder = new GLSubpassBuilder();
		subBuilder.subpass = new GLSubpass();
		subpasses.add(subBuilder.subpass);
		builder.accept(subBuilder);
		return this;
	}

	@Override
	public ZERenderPassBuilder addSubpassDependency(Integer source, Integer destination,
			ZEPipelineStage[] completeStages, ZEPipelineStage[] waitStages, ZEAccessType[] completeAccesses,
			ZEAccessType[] waitAccesses) {
		// OpenGL doesn't care about your dependency hints
		return this;
	}

	@Override
	public ZERenderPass build() {
		return new GLRenderPass(usages, subpasses.toArray(new GLSubpass[0]));
	}

}
