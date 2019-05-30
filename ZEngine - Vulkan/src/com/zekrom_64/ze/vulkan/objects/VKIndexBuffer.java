package com.zekrom_64.ze.vulkan.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;

import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.mem.ZEMapMode;
import com.zekrom_64.ze.base.util.ZEPrimitiveType;

public class VKIndexBuffer implements ZEIndexBuffer {

	public final VKBuffer buffer;
	public final ZEPrimitiveType indexType;
	
	public VKIndexBuffer(VKBuffer buf, ZEPrimitiveType indexType) {
		buffer = buf;
		this.indexType = indexType;
	}
	
	@Override
	public ZEPrimitiveType getIndexType() {
		return indexType;
	}

	@Override
	public ByteBuffer mapMemory(ZEMapMode mode) {
		return buffer.mapMemory(mode);
	}

	@Override
	public Pointer<?> mapMemoryRaw(ZEMapMode mode) {
		return buffer.mapMemoryRaw(mode);
	}

	@Override
	public void unmapMemory() {
		buffer.unmapMemory();
	}

	@Override
	public boolean isMapped() {
		return buffer.isMapped();
	}

	@Override
	public boolean isHostAccessible() {
		return buffer.isHostAccessible();
	}

	@Override
	public long size() {
		return buffer.size();
	}

}
