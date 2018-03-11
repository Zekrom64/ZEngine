package com.zekrom_64.ze.base.backend.render.pipeline;

/** A pipeline object defines a rendering pipeline. Pipelines are by default non-modifiable, though some backends might
 * support modifiable pipelines or derivative pipelines.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPipeline {
	
	/** A vertex input is a location in a render pipeline where a vertex buffer can be bound.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZEVertexInput { }
	
	/** An attachment bind point is a location in a render pipeline where a shader attachment can be bound.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZEPipelineBindPoint {
		
		/** Gets the type of this attachment bind point.
		 * 
		 * @return Attachment type
		 */
		public ZEPipelineBindType getType();
		
	}
	
	/** Gets all the vertex inputs in the pipeline.
	 * 
	 * @return Vertex inputs
	 */
	public ZEVertexInput[] getVertexInputs();
	
	/** Gets all the bind points in the pipeline.
	 * 
	 * @return Bind points
	 */
	public ZEPipelineBindPoint[] getBindPoints();
	
}
