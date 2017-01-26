package com.zekrom_64.ze.vulkan;

import com.zekrom_64.ze.base.backend.render.ZETexture;

public class VKTexture implements ZETexture {

	public final long image;
	public final int imageLayout;
	public final long imageView;
	public final long imageMemory;
	
	public VKTexture(long img, int imgLayout, long imgView, long imgMem) {
		image = img;
		imageLayout = imgLayout;
		imageView = imgView;
		imageMemory = imgMem;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

}
