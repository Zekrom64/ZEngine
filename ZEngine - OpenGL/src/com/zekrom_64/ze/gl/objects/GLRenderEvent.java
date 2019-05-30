package com.zekrom_64.ze.gl.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;

public class GLRenderEvent implements ZERenderEvent {

	private volatile boolean state;
	List<CountDownLatch> latches = new ArrayList<>();

	@Override
	public boolean isSet() {
		return state;
	}

	@Override
	public void set() {
		state = true;
		synchronized(latches) {
			for(CountDownLatch l : latches) l.countDown();
			latches.clear();
		}
	}

	@Override
	public void reset() {
		state = false;
	}
	
}
