package com.zekrom_64.ze.voxel.blocktypes;

import java.util.Random;

import com.zekrom_64.ze.voxel.world.ZEWorld;

/** Interface for blocks that are growable like a plant
 * 
 * @author Zekrom_64
 *
 */
public interface ZEBlockGrowable {

	/** Called when the block should be grown.
	 * 
	 * @param w The world to use
	 * @param x The X coordinate of the block
	 * @param y The Y coordinate of the block
	 * @param z THe Z coordinate of the block
	 * @param rand Random number source
	 */
	public void onGrowTick(ZEWorld w, int x, int y, int z, Random rand);
	
}
