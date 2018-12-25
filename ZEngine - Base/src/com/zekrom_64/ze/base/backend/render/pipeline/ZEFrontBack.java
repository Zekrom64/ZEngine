package com.zekrom_64.ze.base.backend.render.pipeline;

/** Enumeration for selecting the back and/or front of a polygon.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEFrontBack {
	
	/** "Front" of polygon */
	FRONT(true, false),
	/** "Back" of polygon */
	BACK(false, true),
	/** Both front and back */
	FRONT_BACK(true, true);
	
	/** Boolean value if the side is included in this value */
	public boolean front, back;
	
	private ZEFrontBack(boolean f, boolean b) {
		front = f;
		back = b;
	}
	
}