package com.zekrom_64.ze.content.inventory;

/** Interface for objects that can have items extracted from them.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEExtractable {
	
	/** Extracts items from this inventory, or returns null if there is none.
	 * 
	 * @return Items extracted
	 */
	public ZEItemStack extract();
	
	/** Attempts to extract the number of items from this inventory, and returns a stack with
	 * the same or less number of items.
	 * 
	 * @param nItems Maximum number of items to extract
	 * @return The items extracted
	 */
	public ZEItemStack extract(int nItems);

}
