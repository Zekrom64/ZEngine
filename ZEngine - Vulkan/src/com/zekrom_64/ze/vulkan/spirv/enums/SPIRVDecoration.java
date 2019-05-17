package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVDecoration {

	RELAXED_PRECISION,
	SPEC_ID,
	BLOCK,
	BUFFER_BLOCK,
	ROW_MAJOR,
	COL_MAJOR,
	ARRAY_STRIDE,
	MATRIX_STRIDE,
	GLSL_SHARED,
	GLSL_PACKED,
	C_PACKED,
	BUILT_IN,
	NO_PERSPECTIVE,
	FLAT,
	PATCH,
	CENTROID,
	SAMPLE,
	INVARIANT,
	RESTRICT,
	ALIASED,
	VOLATILE,
	CONSTANT,
	COHERENT,
	NON_WRITABLE,
	NON_READABLE,
	UNIFORM,
	UNIFORM_ID,
	SATURATED_CONVERSION,
	STREAM,
	LOCATION,
	COMPONENT,
	INDEX,
	BINDING,
	DESCRIPTOR_SET,
	OFFSET,
	XFB_BUFFER,
	XFB_STRIDE,
	FUNC_PARAM_ATTR,
	FP_ROUNDING_MODE,
	FP_FAST_MATH_MODE,
	LINKAGE_ATTRIBUTES,
	NO_CONTRACTION,
	INPUT_ATTACHMENT_INDEX,
	ALIGNMENT,
	MAX_BYTE_OFFSET,
	ALIGNMENT_ID,
	MAX_BYTE_OFFSET_ID,
	
	// SPV_KHR_no_integer_wrap_decoration
	NO_SIGNED_WRAP(4469),
	NO_UNSIGNED_WRAP(4470),
	
	// SPV_AMD_shader_explicit_vertex_parameter
	EXPLICIT_INTERP_AMD(4999),
	
	// SPV_NV_sample_mask_override_coverage
	OVERRIDE_COVERAGE_NV(5248),
	
	// SPV_NV_geometry_shader_passthrough
	PASSTHROUGH_NV(5250),
	
	VIEWPORT_RELATIVE_NV(5252),
	
	// SPV_NV_stereo_view_rendering
	SECONDARY_VIEWPORT_RELATIVE_NV(5256),
	
	// SPV_NV_mesh_shader
	PER_PRIMITIVE_NV(5271),
	PER_VIEW_NV(5272),
	PER_TASK_NV(5273),
	
	// SPV_NV_fragment_shader_barycentric
	PER_VERTEX_NV(5285),
	
	NON_UNIFORM_EXT(5300),
	
	COUNTER_BUFFER(5634),
	USER_SEMANTIC(5635),
	
	// SPV_EXT_physical_storage_buffer
	RESTRICT_POINTER_EXT(5355),
	ALIASED_POINTER_EXT(5356);
	
	public final int value;
	
	private SPIRVDecoration() {
		value = ordinal();
	}
	
	private SPIRVDecoration(int val) {
		value = val;
	}
	
	private static SPIRVDecoration[] linearValues;
	static {
		ArrayList<SPIRVDecoration> vals = new ArrayList<>();
		for(SPIRVDecoration e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVDecoration[0]);
	}
	
	public static SPIRVDecoration parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVDecoration eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVDecoration eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
