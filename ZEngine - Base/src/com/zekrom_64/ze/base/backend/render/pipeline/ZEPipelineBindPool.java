package com.zekrom_64.ze.base.backend.render.pipeline;

/** A pipeline bind pool is a memory pool for pipeline binding sets.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPipelineBindPool {

	/** Gets the binding format for the pool.
	 * 
	 * @return Binding format
	 */
	public ZEPipelineBindLayout getFormat();
	
	/** Gets the maximum number of binding sets available.
	 * 
	 * @return Maximum number of binding sets
	 */
	public int getMaxBindSets();
	
	/** Gets the number of binding sets currently allocated.
	 * 
	 * @return Number of sets currently allocated
	 */
	public int getNumAllocatedSets();
	
	/** Frees an allocated bind set.
	 * 
	 * @param bindSet Bind set to free
	 */
	public void free(ZEPipelineBindSet bindSet);
	
	/** Creates a new allocator to allocate bind sets with.
	 * 
	 * @return Bind set allocator
	 */
	public ZEPipelineBindSet allocate();
	
}
