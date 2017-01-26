package com.zekrom_64.ze.base.threading;

/** An event is a threading utility that stores a boolean state, being set or reset.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEEvent {

	/** Tests if the event is set.
	 * 
	 * @return If the event is set
	 */
	public boolean isSet();
	
	/** Sets the event. */
	public void set();
	
	/** Resets the event. */
	public void reset();
	
}
