package com.zekrom_64.ze.base.backend.render.pipeline;

/** Builder for pipeline bind layouts.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPipelineBindLayoutBuilder {

	/** Adds a binding to the bind layout.
	 * 
	 * @param binding The binding location
	 * @param bindType The type of binding
	 * @param arrayCount Number of elements in the 
	 * @param shaderStages
	 */
	public void addBinding(int binding, ZEPipelineBindType bindType, int arrayCount, String[] shaderStages);
	
	/** Builds the pipeline bind layout.
	 * 
	 * @return Pipeline bind layout
	 */
	public ZEPipelineBindLayout build();
	
}
