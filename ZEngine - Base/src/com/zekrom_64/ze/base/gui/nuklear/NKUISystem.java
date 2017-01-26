package com.zekrom_64.ze.base.gui.nuklear;

import org.lwjgl.nuklear.NkAllocator;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.NkMFreeI;
import org.lwjgl.nuklear.NkMallocI;
import org.lwjgl.nuklear.Nuklear;
import org.lwjgl.system.MemoryUtil;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.err.ZEngineException;
import com.zekrom_64.ze.base.gui.ZEUISystem;

public class NKUISystem implements ZEUISystem {

	private NkContext context = NkContext.create();
	private static final NkAllocator alloc = NkAllocator.create();
	
	static {
		alloc.alloc(new NkMallocI() {

			@Override
			public long invoke(long handle, long old, long size) {
				if (old==0) return MemoryUtil.nmemAlloc(size);
				else return MemoryUtil.nmemRealloc(old, size);
			}
			
		});
		alloc.mfree(new NkMFreeI() {

			@Override
			public void invoke(long handle, long ptr) {
				MemoryUtil.nmemFree(ptr);
			}
			
		});
	}
	
	@Override
	public void initialize(ZERenderBackend<?> backend) throws ZEngineException {
		Nuklear.nk_init(context, alloc, null);
	}

	@Override
	public boolean isAccelerated() {
		return false;
	}

	@Override
	public void deinitialize() {
		Nuklear.nk_free(context);
	}
	
}
