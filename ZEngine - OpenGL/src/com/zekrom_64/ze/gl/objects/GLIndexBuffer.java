package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;

import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.mem.ZEMapMode;
import com.zekrom_64.ze.base.util.PrimitiveType;

public class GLIndexBuffer implements ZEIndexBuffer {

	public final GLBuffer parent;
	public final PrimitiveType indexType;
	
	public GLIndexBuffer(GLBuffer buf, PrimitiveType type) {
		parent = buf;
		indexType = type;
	}
	
	@Override
	public ByteBuffer mapMemory(ZEMapMode mode) {
		return parent.mapMemory(mode);
	}

	@Override
	public Pointer<?> mapMemoryRaw(ZEMapMode mode) {
		return parent.mapMemoryRaw(mode);
	}

	@Override
	public void unmapMemory() {
		parent.unmapMemory();
	}

	@Override
	public boolean isMapped() {
		return parent.isMapped();
	}

	@Override
	public boolean isHostAccessible() {
		return parent.isHostAccessible();
	}

	@Override
	public long size() {
		return parent.size();
	}

	@Override
	public PrimitiveType getIndexType() {
		return indexType;
	}

	@Override
	public ZEBufferUsage[] getValidUsages() {
		return parent.getValidUsages();
	}

}
