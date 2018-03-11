package com.zekrom_64.ze.base.backend.render.pipeline;

/** Enumeration containing the different stages of the rendering pipeline.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEPipelineStage {
	/** Start of the pipeline */
	START_OF_PIPELINE,
	/** Draw call indirection */
	DRAW_INDIRECT,
	/** Vertex input reading */
	VERTEX_INPUT,
	/** Vertex shader stage */
	VERTEX_SHADER,
	/** Tessellation control shader stage */
	TESSELLATION_CONTROL_SHADER,
	/** Tessellation evaluation shader stage */
	TESSELLATION_EVALUATION_SHADER,
	/** Geometry shader stage */
	GEOMETRY_SHADER,
	/** Fragment shader stage */
	FRAGMENT_SHADER,
	/** Early fragment tests */
	EARLY_FRAGMENT_TEST,
	/** Later fragment tests */
	LATE_FRAGMENT_TEST,
	/** Color output */
	COLOR_OUTPUT,
	/** End of the pipeline */
	END_OF_PIPELINE
}
