package com.zekrom_64.ze.nat;

/** A native memory object can have its memory accessed by native code.
 * 
 * @author Zekrom_64
 *
 */
public interface ZENativeMemory {

	/** Gets the native address of the memory.
	 * 
	 * @return Native address
	 */
	public long getAddress();
	
}
