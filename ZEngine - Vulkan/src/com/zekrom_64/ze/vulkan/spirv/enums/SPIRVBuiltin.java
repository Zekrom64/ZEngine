package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVBuiltin {

	POSITION,
	POINT_SIZE,
	CLIP_DISTANCE,
	CULL_DISTANCE,
	VERTEX_ID,
	INSTANCE_ID,
	PRIMITIVE_ID,
	INVOCATION_ID,
	LAYER,
	VIEWPORT_INDEX,
	TESS_LEVEL_OUTER,
	TESS_LEVEL_INNER,
	TESS_COORD,
	PATCH_VERTICES,
	FRAG_COORD,
	POINT_COORD,
	FRONT_FACING,
	SAMPLE_ID,
	SAMPLE_POSITION,
	SAMPLE_MASK,
	FRAG_DEPTH,
	HELPER_INVOCATION,
	NUM_WORKGROUPS,
	WORKGROUP_SIZE,
	WORKGROUP_ID,
	LOCAL_INVOCATION_ID,
	GLOBAL_INVOCATION_ID,
	LOCAL_INVOCATION_INDEX,
	WORK_DIM,
	GLOBAL_SIZE,
	ENQUEUED_WORKGROUP_SIZE,
	GLOBAL_OFFSET,
	GLOBAL_LINEAR_ID,
	SUBGROUP_SIZE,
	SUBGROUP_MAX_SIZE,
	NUM_SUBGROUPS,
	NUM_ENQUEUED_SUBGROUPS,
	SUBGROUP_ID,
	SUBGROUP_LOCAL_INVOCATION_ID,
	VERTEX_INDEX,
	INSTANCE_INDEX,
	
	// SPV_KHR_shader_ballot
	SUBGROUP_EQ_MASK(4416),
	SUBGROUP_GE_MASK(4417),
	SUBGROUP_GT_MASK(4418),
	SUBGROUP_LE_MASK(4419),
	SUBGROUP_LT_MASK(4420),

	// SPV_KHR_shader_draw_parameters
	BASE_VERTEX(4424),
	BASE_INSTANCE(4425),
	DRAW_INDEX(4426),
	DEVICE_INDEX(4438),
	VIEW_INDEX(4440),
	
	// SPV_AMD_shader_explicit_vertex_parameter
	BARY_COORD_NO_PERSP_AMD(4992),
	BARY_COORD_NO_PERSP_CENTROID_AMD(4993),
	BARY_COORD_NO_PERSP_SAMPLE_AMD(4994),
	BARY_COORD_SMOOTH_AMD(4995),
	BARY_COORD_SMOOTH_CENTROID_AMD(4996),
	BARY_COORD_SMOOTH_SAMPLE_AMD(4997),
	BARY_COORD_PULL_MODE_AMD(4998),
	
	// SPV_EXT_shader_stencil_export
	FRAG_STENCIL_REF_EXT(5014),
	
	// SPV_NV_viewport_array2
	// SPV_NV_mesh_shader
	VIEWPORT_MASK_NV(5253),
	
	// SPV_NV_stereo_view_rendering
	SECONDARY_POSITION_NV(5257),
	SECONDARY_VIEWPORT_MASK_NV(5258),
	
	// SPV_NVX_multiview_per_view_attributes
	// SPV_NV_mesh_shader
	POSITION_PER_VIEW_NV(5261),
	VIEWPORT_MASK_PER_VIEW_NV(5262),
	
	// SPV_EXT_fragment_fully_covered
	FULLY_COVERED_EXT(5264),
	
	// SPV_NV_mesh_shader
	TASK_COUNT_NV(5274),
	PRIMITIVE_COUNT_NV(5275),
	PRIMITIVE_INDICES_NV(5276),
	CLIP_DISTANCE_PER_VIEW_NV(5277),
	CULL_DISTANCE_PER_VIEW_NV(5278),
	LAYER_PER_VIEW_NV(5279),
	MESH_VIEW_COUNT_NV(5280),
	MESH_VIEW_INDICES_NV(5281),
	
	// SPV_NV_fragment_shader_barycentric
	BARY_COORD_NV(5286),
	BARY_COORD_NO_PERSP_NV(5287),
	
	// SPV_EXT_fragment_invocation_density
	// SPV_NV_shading_rate
	FRAG_SIZE_EXT(5292),
	FRAG_INVOCATION_COUNT_EXT(5293),
	
	// SPV_NV_ray_tracing
	LAUNCH_ID_NV(5319),
	LAUNCH_SIZE_NV(5320),
	WORLD_RAY_ORIGIN_NV(5321),
	WORLD_RAY_DIRECTION_NV(5322),
	OBJECT_RAY_ORIGIN_NV(5323),
	OBJECT_RAY_DIRECTION_NV(5324),
	RAY_TMIN_NV(5325),
	RAY_TMAX_NV(5326),
	INSTANCE_CUSTOM_INDEX_NV(5327),
	OBJECT_TO_WORLD_NV(5330),
	WORLD_TO_OBJECT_NV(5331),
	HIT_T_NV(5332),
	HIT_KIND_NV(5333),
	INCOMING_RAY_FLAGS_NV(5351);
	
	public final int value;
	
	private SPIRVBuiltin() {
		value = ordinal();
	}
	
	private SPIRVBuiltin(int val) {
		value = val;
	}
	
	private static SPIRVBuiltin[] linearValues;
	static {
		ArrayList<SPIRVBuiltin> vals = new ArrayList<>();
		for(SPIRVBuiltin e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVBuiltin[0]);
	}
	
	public static SPIRVBuiltin parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVBuiltin eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVBuiltin eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
