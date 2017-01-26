package com.zekrom_64.ze.libav.managed;

public interface AVSeekable {

	public void seekToTime(double time);
	
	public void seekToFrame(int frame);
	
}
