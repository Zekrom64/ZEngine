package com.zekrom_64.ze.voxel;

import com.zekrom_64.ze.content.inventory.ZEItem;

/** An item-block is an item that represents a type of block
 * 
 * @author Zekrom_64
 *
 */
public class ZEItemBlock extends ZEItem {

	/** Creates a new item-block for the given block */
	public ZEItemBlock(ZEBlock b) {
		super(b.unlocalizedName);
	}

}
