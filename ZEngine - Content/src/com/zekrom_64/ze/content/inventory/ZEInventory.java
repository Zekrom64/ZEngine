package com.zekrom_64.ze.content.inventory;

/** Interface for objects that can store items. Inventories may have slots implemented by {@link com.zekrom_64.ze.content.inventory.ZESlot Slot}
 * that can restrict the flow of items in and out and provide a way for players to access the inventory. If an inventory does not have a
 * way to implement slots, then it is 
 * 
 * @author Zekrom_64
 *
 */
public interface ZEInventory extends ZEInsertable, ZEExtractable {

	/** Gets all the item stacks in this inventory. The stacks returned by this should
	 * not be modified.
	 * 
	 * @return All item stacks in this inventory
	 */
	public ZEItemStack[] getItemStacks();
	
	/** Gets the slots of this inventory, or a zero-length array if this 
	 * 
	 * @return
	 */
	public ZESlot[] getSlots();
	
}
