package com.zekrom_64.ze.libav.managed;

import com.zekrom_64.ze.base.image.ZEImage;

@FunctionalInterface
public interface AVSubtitleConsumer {

	public void consumeSubtitle(String asText, ZEImage asImage, int imgX, int imgY);
	
}
