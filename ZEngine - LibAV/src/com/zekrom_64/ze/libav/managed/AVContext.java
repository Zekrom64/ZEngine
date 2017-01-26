package com.zekrom_64.ze.libav.managed;

public interface AVContext {

	public double getCurrentTime();
	
	public int getCurrentFrame();
	
	public void setRunning(boolean running);
	
	public boolean isRunning();
	
}
