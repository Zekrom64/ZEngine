package com.zekrom_64.ze.vulkan.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;

import com.zekrom_64.ze.base.backend.render.ZEBuffer;
import com.zekrom_64.ze.base.mem.ZEMapMode;

/** Vulkan implementation of a {@link ZEBuffer}.
 * 
 * @author Zekrom_64
 *
 */
public class VKBuffer implements ZEBuffer {

	/** The memory buffer handle */
	public final long buffer;
	/** The size of the memory buffer */
	public final long size;
	/** The device memory handle */
	public final long memory;
	/** THe offset into that device memory */
	public final long memoryOffset;
	/** The device owning this memory buffer */
	public final VkDevice device;
	/** THe flags of the memory buffer */
	public final int flags;
	private Pointer<?> memPtr;
	private ByteBuffer memBuf;
	
	public VKBuffer(VkDevice dev, long buf, long size, long mem, long memoff, int flags) {
		device = dev;
		this.size = size;
		buffer = buf;
		memory = mem;
		memoryOffset = memoff;
		this.flags = flags;
	}

	@Override
	public ByteBuffer mapMemory(ZEMapMode mode) {
		if (memPtr == null) map();
		return memBuf;
	}

	@Override
	public Pointer<?> mapMemoryRaw(ZEMapMode mode) {
		if (memPtr == null) map();
		return memPtr;
	}
	
	@SuppressWarnings("deprecation")
	private void map() {
		MemoryStack sp = MemoryStack.stackGet();
		int bp = sp.getPointer();
		PointerBuffer pMem = sp.mallocPointer(1);
	
		int err = VK10.vkMapMemory(device, memory, memoryOffset, size, 0, pMem);
		if (err == VK10.VK_SUCCESS) {
			if (size <= Integer.MAX_VALUE) memBuf = MemoryUtil.memByteBuffer(pMem.get(0), (int)size);
			memPtr = Pointer.pointerToAddress(pMem.get(0));
		}
		
		sp.setPointer(bp);
	}

	@Override
	public void unmapMemory() {
		if (memPtr != null) {
			VK10.vkUnmapMemory(device, memPtr.getPeer());
			memPtr = null;
			memBuf = null;
		}
	}

	@Override
	public boolean isMapped() {
		return memPtr != null;
	}

	@Override
	public boolean isHostAccessible() {
		return (flags & VK10.VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT) != 0;
	}

	@Override
	public long size() {
		return size;
	}
	
}
