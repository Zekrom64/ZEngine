package com.zekrom_64.ze.content.inventory;

/** Interface for a slot in an inventory.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESlot {

	/** Gets the stack in the slot. The returned stack should not be modified.
	 * 
	 * @return Stack in slot
	 */
	public ZEItemStack getStack();
	
	/** Attempts to extract the number of items from the slot, and returns an itemstack with the most
	 * that can be extracted, or null if the slot is empty.
	 * 
	 * @param nItems Maximum number of items to extract
	 * @return Items extracted, or null
	 */
	public ZEItemStack extract(int nItems);
	
	/** Extracts the item stack from this slot. Null is returned if the slot is empty.
	 * 
	 * @return Item stack in slot, or null
	 */
	public ZEItemStack extract();
	
	/** Attempts to insert an item stack into this slot. Any items that could not be inserted are returned.
	 * 
	 * @param stack Items inserted
	 * @return Any items left over
	 */
	public ZEItemStack insert(ZEItemStack stack);
	
	/** Tests to see how many items can be inserted into the slot, or returns 0 if no items can be inserted.
	 * 
	 * @param stack Items to insert
	 * @return The number of items from the stack that can be inserted
	 */
	public int canInsert(ZEItemStack stack);
	
}
