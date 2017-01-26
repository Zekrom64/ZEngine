package com.zekrom_64.ze.logic.gpu;

import java.nio.ByteBuffer;

/** Interface for objects that can accept GPU output.
 * 
 * @author Zekrom_64
 *
 */
public interface IGPUTarget {

	/** Called to upload data to the area specified.
	 * 
	 * @param rgba
	 * @param width
	 * @param height
	 */
	public void upload(ByteBuffer rgba, int width, int height);
	
	/** 
	 * 
	 * @param rgba
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void uploadArea(ByteBuffer rgba, int x, int y, int width, int height);
	
}
