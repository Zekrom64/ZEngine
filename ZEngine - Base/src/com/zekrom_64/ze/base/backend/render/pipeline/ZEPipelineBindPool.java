package com.zekrom_64.ze.base.backend.render.pipeline;

/** A pipeline bind pool is a memory pool for pipeline binding sets.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPipelineBindPool {
	
	/** Frees an allocated bind set.
	 * 
	 * @param bindSet Bind set to free
	 */
	public void free(ZEPipelineBindSet bindSet);
	
	/** Creates a new allocator to allocate bind sets with.
	 * 
	 * @param layouts The layouts to use with the bind set
	 * @return Bind set allocator
	 */
	public ZEPipelineBindSet allocate(ZEPipelineBindLayout ... layouts);
	
}
