package com.zekrom_64.ze.voxel;

import java.util.function.Function;

/** A block defines the behavior of a block in the world.
 * 
 * @author Zekrom_64
 *
 */
public abstract class ZEBlock {
	
	/** The maximum number of blocks that can be registered. */
	public static final int MAX_BLOCKS = 0xFFFF;
	private static ZEBlock[] blocks = new ZEBlock[MAX_BLOCKS];
	private static int nextBlock = 1;
	
	/** Gets a block by id. If the id is less than 0 or greater than or equal to {@link #MAX_BLOCKS}, <b>null</b>
	 * is returned. If no block is found, <b>null</b> is returned.
	 * 
	 * @param id Block id
	 * @return The block linked to the id, or <b>null</b>
	 */
	public static ZEBlock byId(int id) {
		if (id<0||id>=MAX_BLOCKS) return null;
		return blocks[id];
	}
	
	/** Finds a block by unlocalized name. If no block is found with the same unlocalized name, <b>null</b> is
	 * returned.
	 * 
	 * @param name Unlocalized name
	 * @return The block linked to the unlocalized name, or <b>null</b>
	 */
	public static ZEBlock byUnlocalizedName(String name) {
		for(int i = 0; i < MAX_BLOCKS; i++) {
			ZEBlock b = blocks[i];
			if (b==null) return null;
			if (b.unlocalizedName.equals(name)) return b;
		}
		return null;
	}
	
	/** The unlocalized name of the block */
	public final String unlocalizedName;
	/** The item associated with the block */
	public final ZEItemBlock item;
	/** Block id */
	public final int id;
	
	/** Creates a new block and registers it in the block registry.
	 * 
	 * @param unlocalizedName The unlocalized name of the block
	 */
	public ZEBlock(String unlocalizedName) {
		if (unlocalizedName==null) throw new NullPointerException("Block name cannot be null!");
		if (nextBlock==MAX_BLOCKS) throw new IndexOutOfBoundsException("Out of block ids");
		this.unlocalizedName = unlocalizedName;
		id = nextBlock++;
		blocks[id] = this;
		item = new ZEItemBlock(this);
	}
	
	public ZEBlock(String unlocalizedName, Function<ZEBlock,ZEItemBlock> fn) {
		if (unlocalizedName==null) throw new NullPointerException("Block name cannot be null!");
		if (nextBlock==MAX_BLOCKS) throw new IndexOutOfBoundsException("Out of block ids");
		this.unlocalizedName = unlocalizedName;
		id = nextBlock++;
		blocks[id] = this;
		item = fn.apply(this);
	}
	
}
