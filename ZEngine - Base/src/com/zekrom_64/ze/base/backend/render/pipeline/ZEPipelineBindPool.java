package com.zekrom_64.ze.base.backend.render.pipeline;

import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEPipelineBindPoint;

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
	public ZEPassDependency getFormat();
	
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
	
	/** A bind set allocator provides an interface to allocate 
	 * bind sets from a pool. A bind set has an internal state
	 * mapping attachment points to bind objects. Bind
	 * objects can be bound or unbound using
	 * {@link #set(ZEPipelineBindPoint, Object)}. When the
	 * {@link #allocate()} method is called, a new bind set is
	 * allocated from the pool with the allocator's current settings.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public interface ZEPipelineBindSetAllocator {
		
		/** Sets the bound object for a given bind point. If the object to
		 * bind is <b>null</b>, the binding is removed.
		 * 
		 * @param bindPoint Bind point
		 * @param bindObject Object to bind, or <b>null</b>
		 */
		public void set(ZEPipelineBindPoint bindPoint, Object bindObject);
		
		/** Allocates a new bind set from the parent bind pool with
		 * the current binding settings of the allocator.
		 * 
		 * @return Allocated bind set, or <b>null</b> if the bind pool is full
		 */
		public ZEPipelineBindSet allocate();
		
	}
	
	/** Frees an allocated bind set.
	 * 
	 * @param bindSet Bind set to free
	 */
	public void free(ZEPipelineBindSet bindSet);
	
	/** Creates a new allocator to allocate bind sets with.
	 * 
	 * @return Bind set allocator
	 */
	public ZEPipelineBindSetAllocator allocate();
	
}
