package com.zekrom_64.ze.base.input;

/** An object that can send input events to any number of input listeners.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEInputSource {

	/** Adds an input listener to this source. There should not be duplicates of any listener
	 * in the source's internal list.
	 * 
	 * @param l Input listener to add
	 */
	public void addInputListener(ZEInputListener l);
	
	/** Removes an input listener from this source if the source's internal list contains it.
	 * 
	 * @param l Input listener to remove
	 */
	public void removeInputListener(ZEInputListener l);
	
}
