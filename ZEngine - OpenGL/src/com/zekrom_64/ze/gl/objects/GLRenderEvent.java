package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.ZERenderEvent;

public class GLRenderEvent implements ZERenderEvent {

	public boolean state;

	@Override
	public boolean isSet() {
		return state;
	}

	@Override
	public void set() {
		state = true;
	}

	@Override
	public void reset() {
		state = false;
	}
	
}
