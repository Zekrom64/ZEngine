package com.zekrom_64.ze.vulkan.objects;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;

public class VKSemaphore implements ZERenderSemaphore {

	public final long semaphore;
	
	public VKSemaphore(long sem) {
		semaphore = sem;
	}
	
}
