package com.zekrom_64.ze.base.input;

/** An object that can send window events to any number of window listeners.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEWindowSource {

	/** Adds a window listener to this source. There should be no duplicates of any listener
	 * in this source's internal list.
	 * 
	 * @param l Window listener to add
	 */
	public void addWindowListener(ZEWindowListener l);
	
	/** Removes a window listener from this source if the source's internal list contains it.
	 * 
	 * @param l Window listener to remove
	 */
	public void removeWindowListener(ZEWindowListener l);
	
}
