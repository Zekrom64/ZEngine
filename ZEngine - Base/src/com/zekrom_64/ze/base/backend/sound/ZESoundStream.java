package com.zekrom_64.ze.base.backend.sound;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;

public interface ZESoundStream {

	public AudioFormat getFormat();
	
	public boolean isSeekable();
	
	public void seekToFrame(int frame) throws IOException ;
	
	public int getCurrentFrame();
	
	public boolean isResetable();
	
	public void reset() throws IOException;
	
}
