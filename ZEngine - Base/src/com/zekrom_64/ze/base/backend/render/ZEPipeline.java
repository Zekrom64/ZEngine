package com.zekrom_64.ze.base.backend.render;

/** A pipeline object defines a rendering pipeline. Pipelines are by default non-modifiable, though some backends might
 * support modifiable pipelines or derivative pipelines.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPipeline {
	
	/** A vertex bind point is a location in a render pipeline where a vertex buffer can be bound.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZEVertexBindPoint { }
	
	/** Gets all the vertex bind points in the pipeline.
	 * 
	 * @return Vertex bind points
	 */
	public ZEVertexBindPoint[] getVertexBindPoints();
	
}
