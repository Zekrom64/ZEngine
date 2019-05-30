package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder.ZEAttachmentUsage;

public class GLRenderPass implements ZERenderPass {

	public final ZEAttachmentUsage[] attachmentUsages;
	public static class GLSubpass {
		public Integer[] inputAttachments;
		public Integer[] colorAttachments;
		public Integer depthStencilAttachment;
	}
	public final GLSubpass[] subpasses;
	
	public GLRenderPass(ZEAttachmentUsage[] usages, GLSubpass[] subpasses) {
		this.attachmentUsages = usages;
		this.subpasses = subpasses;
	}
	
}
