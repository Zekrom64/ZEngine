package com.zekrom_64.ze.content.inventory;

/** An item defines the behavior for a specific type of item.
 * 
 * @author Zekrom_64
 *
 */
public abstract class ZEItem {

	/** The maximum number of items that can be registered. */
	public static final int MAX_ITEMS = 0xFFFF;
	private static ZEItem[] items = new ZEItem[MAX_ITEMS];
	private static int nextItem = 1;
	
	/** Finds an item by id. If the id is less than 0 or greater than or equal to {@link #MAX_ITEMS}, <b>null</b> is
	 * returned. If the id does not map to an item, this returns <b>null</b>.
	 * 
	 * @param id Item id
	 * @return The item linked to the id, or <b>null</b>
	 */
	public static ZEItem byId(int id) {
		if (id<0||id>=MAX_ITEMS) return null;
		return items[id];
	}
	
	/** Finds an item by unlocalized name. If no item is found with the same unlocalized name, <b>null</b is returned.
	 * 
	 * @param uname THe unlocalized name
	 * @return The item linked to the unlocalized name, or <b>null</b>
	 */
	public static ZEItem byUnlocalizedName(String uname) {
		for(int i = 0; i < MAX_ITEMS; i++) {
			ZEItem item = items[i];
			if (item==null) return null;
			if (item.unlocalizedName.equals(uname)) return item;
		}
		return null;
	}
	
	/** The unlocalized name of the item */
	public final String unlocalizedName;
	/** The numeric id of the item */
	public final int id;
	
	/** Creates a new item and registers it in the item registry.
	 * 
	 * @param uname The unlocalized name of the item
	 */
	public ZEItem(String uname) {
		if (uname==null) throw new NullPointerException("Item name cannot be null");
		if (nextItem==MAX_ITEMS) throw new IndexOutOfBoundsException("Out of item ids");
		unlocalizedName = uname;
		id = nextItem++;
		items[id] = this;
	}
	
}
