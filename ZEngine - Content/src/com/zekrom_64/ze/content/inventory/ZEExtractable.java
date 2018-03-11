package com.zekrom_64.ze.content.inventory;

import java.util.function.Predicate;

/** Interface for objects that can have items extracted from them.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEExtractable {
	
	/** Extracts items from this inventory, or returns <b>null</b> if there is none.
	 * 
	 * @return Items extracted
	 */
	public ZEItemStack extract();
	
	/** Attempts to extract the number of items from this inventory, and returns a stack with
	 * the same or less number of items. If no items can be extracted, <b>null</b> is returned.
	 * 
	 * @param nItems Maximum number of items to extract
	 * @return Items extracted
	 */
	public ZEItemStack extract(int nItems);
	
	/** Extracts items from this inventory if they match the given filter. If no items
	 * can be extracted, <b>null</b> is returned.
	 * 
	 * @param filter Filter function
	 * @return Items extracted
	 */
	public ZEItemStack extract(Predicate<ZEItemStack> filter);

}
