package com.zekrom_64.ze.gl.objects;

import java.util.function.Consumer;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEAccessType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;

public class GLRenderPassBuilder implements ZERenderPassBuilder {

	private ZEAttachmentUsage[] usages;
	private static class GLSubpass {
		
	}
	
	@Override
	public ZERenderPassBuilder setAttachmentUsages(ZEAttachmentUsage... usages) {
		this.usages = usages;
		return this;
	}
	
	private class GLSubpassBuilder implements ZESubpassBuilder {

		@Override
		public ZESubpassBuilder mapInputAttachment(int index, Integer attachmentIndex, ZETextureLayout layout) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ZESubpassBuilder mapColorAttachment(int index, Integer attachmentIndex, ZETextureLayout layout) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ZESubpassBuilder mapDepthStencilAttachment(Integer attachmentIndex, ZETextureLayout layout) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ZESubpassBuilder preserveAttachment(int attachmentIndex) {
			return this; // OpenGL doesn't randomly trash attachments because it feels like it
		}
		
	}
	
	@Override
	public ZERenderPassBuilder addSubpass(Consumer<ZESubpassBuilder> builder) {
		
		builder.accept(builder);
		return this;
	}

	@Override
	public ZERenderPassBuilder addSubpassDependency(Integer source, Integer destination,
			ZEPipelineStage[] completeStages, ZEPipelineStage[] waitStages, ZEAccessType[] completeAccesses,
			ZEAccessType[] waitAccesses) {
		// OpenGL doesn't care about your dependency hints
		return this;
	}

}
