package com.zekrom_64.ze.vulkan;

import java.nio.ByteBuffer;

import org.bridj.Pointer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;

import com.zekrom_64.ze.base.backend.render.ZEBuffer;
import com.zekrom_64.ze.base.err.ZEngineInternalException;

public class VKBuffer implements ZEBuffer {

	public final long buffer;
	public final long size;
	public final long memory;
	public final long memoryOffset;
	public final VkDevice device;
	private long mappedMemory = 0;
	
	public VKBuffer(VkDevice dev, long buf, long size, long mem, long memoff) {
		device = dev;
		this.size = size;
		buffer = buf;
		memory = mem;
		memoryOffset = memoff;
	}

	@Override
	public ByteBuffer mapMemory() {
		if (size>Integer.MAX_VALUE)
			ZEngineInternalException.throwInternallyNoExcept("Cannot map ByteBuffer to buffer larger than Integer.MAX_VALUE");
		if (mappedMemory!=0) return MemoryUtil.memByteBuffer(mappedMemory, (int)size);
		MemoryStack sp = MemoryStack.stackGet();
		int bp = sp.getPointer();
		PointerBuffer pMem = sp.pointers(0);
		int err = VK10.vkMapMemory(device, memory, memoryOffset, size, 0, pMem);
		if (err!=VK10.VK_SUCCESS) {
			sp.setPointer(bp);
			return null;
		}
		mappedMemory = pMem.get();
		sp.setPointer(bp);
		return MemoryUtil.memByteBuffer(mappedMemory, (int)size);
	}

	@Override
	public Pointer<?> mapMemoryRaw() {
		if (mappedMemory!=0) {
			@SuppressWarnings("deprecation")
			Pointer<?> ptr = Pointer.pointerToAddress(mappedMemory, size);
			return ptr;
		}
		MemoryStack sp = MemoryStack.stackGet();
		int bp = sp.getPointer();
		PointerBuffer pMem = sp.pointers(0);
		int err = VK10.vkMapMemory(device, memory, memoryOffset, size, 0, pMem);
		if (err!=VK10.VK_SUCCESS) {
			sp.setPointer(bp);
			return Pointer.NULL;
		}
		mappedMemory = pMem.get();
		sp.setPointer(bp);
		@SuppressWarnings("deprecation")
		Pointer<?> ptr = Pointer.pointerToAddress(mappedMemory, size);
		return ptr;
	}

	@Override
	public void unmapMemory() {
		VK10.vkUnmapMemory(device, mappedMemory);
		mappedMemory = 0;
	}
	
	@Override
	protected void finalize() {
		if (mappedMemory!=0) VK10.vkUnmapMemory(device, mappedMemory);
	}
	
}
