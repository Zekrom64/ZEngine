package com.zekrom_64.ze.base.gui;

public interface ZEFrame {

	public void setSize(int w, int h);
	
	public int getWidth();
	
	public int getHeight();
	
	public void setPosition(int x, int y);
	
	public int getX();
	
	public int getY();
	
	public String getTitle();
	
	public void setTitle(String title);
	
	public ZEFrameGraphics getGraphics();
	
}
