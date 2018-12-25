package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.obj.ZEFramebufferBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;

public class GLFramebufferBuilder implements ZEFramebufferBuilder {

	
	
	@Override
	public void setSize(int width, int height, int layers) {
		
	}

	@Override
	public int addAttachment(ZETexture tex) {
		return 0;
	}

}
