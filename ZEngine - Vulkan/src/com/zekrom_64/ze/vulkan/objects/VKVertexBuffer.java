package com.zekrom_64.ze.vulkan.objects;

import org.lwjgl.vulkan.VkDevice;

import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;

public class VKVertexBuffer extends VKBuffer implements ZEVertexBuffer {

	public VKVertexBuffer(VKBuffer buf) {
		this(buf.device, buf.buffer, buf.size, buf.memory, buf.memoryOffset, buf.flags);
	}
	
	public VKVertexBuffer(VkDevice dev, long buf, long size, long mem, long memoff, int flags) {
		super(dev, buf, size, mem, memoff, flags);
	}

}
