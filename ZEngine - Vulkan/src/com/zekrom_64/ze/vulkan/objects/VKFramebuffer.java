package com.zekrom_64.ze.vulkan.objects;

import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;

public class VKFramebuffer implements ZEFramebuffer {

	public final long framebuffer;
	
	public VKFramebuffer(long vkframebuf) {
		framebuffer = vkframebuf;
	}
	
}
