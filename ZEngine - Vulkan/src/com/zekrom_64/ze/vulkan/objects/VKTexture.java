package com.zekrom_64.ze.vulkan.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;

import com.zekrom_64.ze.base.backend.render.ZETexture;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.mem.ZEMapMode;

/** Vulkan implementation of a {@link ZETexture}.
 * 
 * @author Zekrom_64
 *
 */
public class VKTexture implements ZETexture {

	public final long image;
	public final int imageLayout;
	public final long imageView;
	public final long imageMemory;
	private int width;
	private int height;
	private ZEPixelFormat pxfmt;
	private Pointer<?> memPtr;
	private ByteBuffer memBuf;
	private VkDevice memDevice;
	private boolean hostAccessible;
	private int memPitch;
	private long memSize;
	private long memOffset;
	
	public VKTexture(long img, int imgLayout, long imgView, long imgMem, ZEPixelFormat format, int w, int h, VkDevice dev, boolean hostAccess, int pitch) {
		image = img;
		imageLayout = imgLayout;
		imageView = imgView;
		imageMemory = imgMem;
		pxfmt = format;
		width = w;
		height = h;
		memDevice = dev;
		hostAccessible = hostAccess;
		memPitch = pitch;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
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
	
		int err = VK10.vkMapMemory(memDevice, imageMemory, memOffset, memSize, 0, pMem);
		if (err == VK10.VK_SUCCESS) {
			if (memSize <= Integer.MAX_VALUE) memBuf = MemoryUtil.memByteBuffer(pMem.get(0), (int)memSize);
			memPtr = Pointer.pointerToAddress(pMem.get(0));
		}
		
		sp.setPointer(bp);
	}

	@Override
	public void unmapMemory() {
		if (memPtr == null) {
			VK10.vkUnmapMemory(memDevice, memPtr.getPeer());
			memPtr = null;
			memBuf = null;
		}
	}

	@Override
	public boolean isMapped() {
		return memBuf != null;
	}

	@Override
	public boolean isHostAccessible() {
		return hostAccessible;
	}

	@Override
	public ZEPixelFormat getPixelFormat() {
		return pxfmt;
	}

	@Override
	public int getMemoryRowPitch() {
		return memPitch;
	}
	
	public static int getVKFormat(ZEPixelFormat pxfmt) {
		switch(pxfmt) {
		case A8R8G8B8_UINT: return -1;
		case R8_UINT: return VK10.VK_FORMAT_R8_UINT;
		case R8G8_UINT: return VK10.VK_FORMAT_R8G8_UINT;
		case R8G8B8_UINT: return VK10.VK_FORMAT_R8G8B8_UINT;
		case R8G8B8A8_UINT: return VK10.VK_FORMAT_R8G8B8A8_UINT;
		case R16_UINT: return VK10.VK_FORMAT_R16_UINT;
		case R16G16_UINT: return VK10.VK_FORMAT_R16G16_UINT;
		case R16G16B16_UINT: return VK10.VK_FORMAT_R16G16B16_UINT;
		case R16G16B16A16_UINT: return VK10.VK_FORMAT_R16G16B16A16_UINT;
		case R32_UINT: return VK10.VK_FORMAT_R32_UINT;
		case R32G32_UINT: return VK10.VK_FORMAT_R32G32_UINT;
		case R32G32B32_UINT: return VK10.VK_FORMAT_R32G32B32_UINT;
		case R32G32B32A32_UINT: return VK10.VK_FORMAT_R32G32B32A32_UINT;
		case R32_FLOAT: return VK10.VK_FORMAT_R32_SFLOAT;
		case R32G32_FLOAT: return VK10.VK_FORMAT_R32G32_SFLOAT;
		case R32G32B32_FLOAT: return VK10.VK_FORMAT_R32G32B32_SFLOAT;
		case R32G32B32A32_FLOAT: return VK10.VK_FORMAT_R32G32B32A32_SFLOAT;
		case UNKNOWN: return VK10.VK_FORMAT_UNDEFINED;
		}
		return -1;
	}

	@Override
	public long size() {
		return memSize;
	}

}
