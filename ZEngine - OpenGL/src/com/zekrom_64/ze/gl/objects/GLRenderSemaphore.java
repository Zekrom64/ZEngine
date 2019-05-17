package com.zekrom_64.ze.gl.objects;

import java.util.concurrent.Semaphore;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;

public class GLRenderSemaphore implements ZERenderSemaphore {

	public final Semaphore semaphore = new Semaphore(1);
	
}
