package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVExecutionMode {

	INVOCATIONS,
	SPACING_EQUAL,
	SPACING_FRACTIONAL_EVEN,
	SPACING_FRACTIONAL_ODD,
	VERTEX_ORDER_CW,
	VERTEX_ORDER_CCW,
	PIXEL_CENTER_INTEGER,
	ORIGIN_UPPER_LEFT,
	ORIGIN_LOWER_LEFT,
	EARLY_FRAGMENT_TESTS,
	POINT_MODE,
	XFB,
	DEPTH_REPLACING,
	DEPTH_GREATER,
	DEPTH_LESS,
	DEPTH_UNCHANGED,
	LOCAL_SIZE,
	LOCAL_SIZE_HINT,
	INPUT_POINTS,
	INPUT_LINES,
	INPUT_LINES_ADJACENCY,
	TRIANGLES,
	INPUT_TRIANGLES_ADJACENCY,
	QUADS,
	ISOLINES,
	OUTPUT_VERTICES,
	OUTPUT_POINTS,
	OUTPUT_LINE_STRIP,
	OUTPUT_TRIANGLE_STRIP,
	VEC_TYPE_HINT,
	CONTRACTION_OFF,
	INITIALIZER,
	FINALIZER,
	SUBGROUP_SIZE,
	SUBGROUPS_PER_WORKGROUP,
	SUBGROUPS_PER_WORKGROUP_ID,
	LOCAL_SIZE_ID,
	LOCAL_SIZE_HINT_ID,
	
	// SPV_KHR_post_depth_coverage
	POST_DEPTH_COVERAGE(4446),
	
	// SPV_KHR_float_controls
	DENORM_PRESERVE(4459),
	DENORM_FLUSH_TO_ZERO(4460),
	SIGNED_ZERO_INF_NAN_PRESERVE(4461),
	ROUNDING_MODE_RTE(4462),
	ROUNDING_MODE_RTZ(4463),
	
	// SPV_EXT_shader_stencil_export
	STENCIL_REF_REPLACING_EXT(5027),
	
	// SPV_NV_mesh_shader
	OUTPUT_LINES_NV(5269),
	OUTPUT_PRIMITIVES_NV(5270),
	OUTPUT_TRIANGLES_NV(5298),
	
	// SPV_NV_compute_shader_derivatives
	DERIVATIVE_GROUP_QUADS_NV(5289),
	DERIVATIVE_GROUP_LINEAR_NV(5290);
	
	public final int value;
	
	private SPIRVExecutionMode() {
		value = ordinal();
	}
	
	private SPIRVExecutionMode(int val) {
		value = val;
	}
	
	private static SPIRVExecutionMode[] linearValues;
	static {
		ArrayList<SPIRVExecutionMode> vals = new ArrayList<>();
		for(SPIRVExecutionMode e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVExecutionMode[0]);
	}
	
	public static SPIRVExecutionMode parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVExecutionMode eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVExecutionMode eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
