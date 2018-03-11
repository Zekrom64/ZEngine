package com.zekrom_64.ze.base.gui.nuklear;

import org.lwjgl.nuklear.NkAllocator;
import org.lwjgl.nuklear.NkContext;
import org.lwjgl.nuklear.Nuklear;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.err.ZEngineException;
import com.zekrom_64.ze.base.gui.ZEUISystem;

@Deprecated()
public class NKUISystem implements ZEUISystem {

	private NkContext context = NkContext.create();
	private static final NkAllocator alloc = NkAllocator.create();
	
	static {
		// TODO: Reimplement allocator
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
