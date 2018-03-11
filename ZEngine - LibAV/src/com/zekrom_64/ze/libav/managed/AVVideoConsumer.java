package com.zekrom_64.ze.libav.managed;

import com.zekrom_64.ze.base.image.ZEImage;

@FunctionalInterface
public interface AVVideoConsumer {
	
	public void consumeVideo(ZEImage frame, double timestamp);
	
}
