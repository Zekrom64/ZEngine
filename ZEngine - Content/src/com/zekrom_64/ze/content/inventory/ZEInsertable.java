package com.zekrom_64.ze.content.inventory;

/** Interface for objects that can have items inserted into them.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEInsertable {
	
	/** Inserts an item stack into this inventory. Any items unable to be inserted are returned.
	 * 
	 * @param stack Items to insert
	 * @return Any items not inserted
	 */
	public ZEItemStack insert(ZEItemStack stack);
	
}
