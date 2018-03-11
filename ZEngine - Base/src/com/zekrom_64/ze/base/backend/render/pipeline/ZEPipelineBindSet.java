package com.zekrom_64.ze.base.backend.render.pipeline;

/** A bind set is a set of attachments to be bound to a render pipeline
 * during a render pass.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPipelineBindSet {

	/** Gets the pool this bind set is allocated from.
	 * 
	 * @return Bind pool
	 */
	public ZEPipelineBindPool getPool();
	
}
